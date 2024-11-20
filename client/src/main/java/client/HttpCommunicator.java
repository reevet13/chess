package client;

import com.google.gson.Gson;
import model.GameData;
import model.GamesList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class HttpCommunicator {

    String baseURL;
    ServerFacade facade;

    public HttpCommunicator(ServerFacade facade, String serverDomain) {
        baseURL = "http://" + serverDomain;
        this.facade = facade;
    }

    public boolean register(String username, String password, String email) {
        var body = Map.of("username", username, "password", password, "email", email);
        var jsonBody = new Gson().toJson(body);
        Map res = request("POST", "/user", jsonBody);
        if (res.containsKey("Error")) {
            return false;
        }
        facade.setAuthToken((String) res.get("authToken"));
        return true;
    }

    public boolean login(String username, String password) {
        var body = Map.of("username", username, "password", password);
        var jsonBody = new Gson().toJson(body);
        Map res = request("POST", "/session", jsonBody);
        if (res.containsKey("Error")) {
            return false;
        }
        facade.setAuthToken((String) res.get("authToken"));
        return true;
    }

    public boolean logout() {
        Map res = request("DELETE", "/session");
        if (res.containsKey("Error")) {
            return false;
        }
        facade.setAuthToken(null);
        return true;
    }

    public int createGame(String gameName) {
        var body = Map.of("gameName", gameName);
        var jsonBody = new Gson().toJson(body);
        Map res = request("POST", "/game", jsonBody);
        if (res.containsKey("error")) {
            return -1;
        }
        double gameID = (double) res.get("gameID");
        return (int) gameID;
    }

    public HashSet<GameData> listGames() {
        String res = requestString("GET", "/game");
        if (res.contains("Error")) {
            return HashSet.newHashSet(8);
        }
        GamesList games = new Gson().fromJson(res, GamesList.class);
        return games.games();
    }

    public boolean joinGame(int gameId, String playerColor) {
        Map body;
        if (playerColor != null) {
            body = Map.of("gameID", gameId, "playerColor", playerColor);
        } else {
            body = Map.of("gameID", gameId);
        }
        var jsonBody = new Gson().toJson(body);
        Map res = request("PUT", "/game", jsonBody);
        return !res.containsKey("Error");
    }

    private Map request (String method, String endpoint) {
        return request(method, endpoint, null);
    }

    private Map request(String method, String endpoint, String body) {
        Map resMap;
        try {
            URI uri = new URI(baseURL + endpoint);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod(method);
            if (facade.getAuthToken() != null) {
                http.addRequestProperty("authorization", facade.getAuthToken());
            }

            if (!Objects.equals(body, null)) {
                http.setDoOutput(true);
                http.addRequestProperty("Content-Type", "application/json");
                try (var outputStream = http.getOutputStream()) {
                    outputStream.write(body.getBytes());
                }
            }

            http.connect();

            try {
                if (http.getResponseCode() == 401) {
                    return Map.of("Error", 401);
                }
            } catch (IOException e) {
                return Map.of("Error", 401);
            }

            try (InputStream resBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                resMap = new Gson().fromJson(inputStreamReader, Map.class);
            }
        } catch (URISyntaxException | IOException e) {
            return Map.of("Error", e.getMessage());
        }
        return resMap;
    }

    private String requestString(String method, String endpoint) {
        return requestString(method, endpoint, null);
    }

    private String requestString(String method, String endpoint, String body) {
        String res;
        try {
            URI uri = new URI(baseURL + endpoint);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod(method);

            if (facade.getAuthToken() != null) {
                http.addRequestProperty("authorization", facade.getAuthToken());
            }

            if (!Objects.equals(body, null)) {
                http.setDoOutput(true);
                http.addRequestProperty("Content-Type", "applilcation/json");
                try (var outputStream = http.getOutputStream()) {
                    outputStream.write(body.getBytes());
                }
            }
            http.connect();

            try {
                if (http.getResponseCode() == 401) {
                    return "Error: 401";
                }
            } catch (IOException e) {
                return "Error: 401";
            }

            try (InputStream resBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                res = readerToString(inputStreamReader);
            }
        } catch (URISyntaxException | IOException e) {
            return String.format("Error: %s", e.getMessage());
        }

        return res;
    }

    private String readerToString(InputStreamReader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int ch; (ch = reader.read()) != -1; ) {
                sb.append((char) ch);
            }
            return sb.toString();
        } catch (IOException e) {
            return "";
        }

    }
}

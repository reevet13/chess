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
import java.util.*;

public class ServerFacade {

    String baseURL = "http://localhost:8080";
    String authToken;

    public ServerFacade() {
    }

    public ServerFacade(String url) {
        baseURL = url;
    }

    public boolean register(String username, String password, String email) {
        var body = Map.of("username", username, "password", password, "email", email);
        var jsonBody = new Gson().toJson(body);
        Map resp = request("POST", "/user", jsonBody);
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = (String) resp.get("authToken");
        return true;
    }

    public boolean login(String username, String password) {
        var body = Map.of("username", username, "password", password);
        var jsonBody = new Gson().toJson(body);
        Map resp = request("POST", "/session", jsonBody);
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = (String) resp.get("authToken");
        return true;
    }

    public boolean logout() {
        Map resp = request("DELETE", "/session", null);
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = null;
        return true;
    }

    public int createGame(String gameName) {
        var body = Map.of("gameName", gameName);
        var jsonBody = new Gson().toJson(body);
        Map resp = request("POST", "/game", jsonBody);
        if (resp.containsKey("Error")) {
            return -1;
        }
        double gameID = (double) resp.get("gameID");
        return (int) gameID;
    }

    public HashSet<GameData> listGames() {
        String resp = requestString("GET", "/game", null);
        if (resp.contains("Error")) {
            return new HashSet<>(8); // returning an empty HashSet if there's an error
        }
        GamesList games = new Gson().fromJson(resp, GamesList.class);
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
        Map resp = request("PUT", "/game", jsonBody);
        return !resp.containsKey("Error");
    }

    // Refactor common request logic
    private <T> T sendRequest(String method, String endpoint, String body, ResponseHandler<T> responseHandler) {
        try {
            URI uri = new URI(baseURL + endpoint);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod(method);

            if (authToken != null) {
                http.addRequestProperty("authorization", authToken);
            }

            if (body != null) {
                http.setDoOutput(true);
                http.addRequestProperty("Content-Type", "application/json");
                try (var outputStream = http.getOutputStream()) {
                    outputStream.write(body.getBytes());
                }
            }

            http.connect();

            try {
                if (http.getResponseCode() == 401) {
                    return responseHandler.handleError("Error: 401");
                }
            } catch (IOException e) {
                return responseHandler.handleError("Error: 401");
            }

            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                return responseHandler.handleSuccess(inputStreamReader);
            }

        } catch (URISyntaxException | IOException e) {
            return responseHandler.handleError("Error: " + e.getMessage());
        }
    }

    // Helper method for request which returns Map
    private Map request(String method, String endpoint, String body) {
        return sendRequest(method, endpoint, body, new ResponseHandler<Map>() {
            @Override
            public Map handleSuccess(InputStreamReader reader) {
                return new Gson().fromJson(reader, Map.class);
            }

            @Override
            public Map handleError(String errorMessage) {
                return Map.of("Error", errorMessage);
            }
        });
    }

    // Helper method for request which returns String
    private String requestString(String method, String endpoint, String body) {
        return sendRequest(method, endpoint, body, new ResponseHandler<String>() {
            @Override
            public String handleSuccess(InputStreamReader reader) {
                return readerToString(reader);
            }

            @Override
            public String handleError(String errorMessage) {
                return errorMessage;
            }
        });
    }

    // Utility method to convert InputStreamReader to String
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

    // Define a response handler interface for flexibility
    private interface ResponseHandler<T> {
        T handleSuccess(InputStreamReader reader);

        T handleError(String errorMessage);
    }
}

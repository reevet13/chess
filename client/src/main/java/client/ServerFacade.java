package client;

import chess.ChessGame;
import chess.ChessMove;
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

    HttpCommunicator http;
    String authToken;
    WebsocketCommunicator ws;
    String serverDomain;

    public ServerFacade() {
        this("localhost:8080");
    }

    public ServerFacade(String serverDomain) throws Exception {
        this.serverDomain = serverDomain;
        http = new HttpCommunicator(this, serverDomain);
        ws = new WebsocketCommunicator(serverDomain);
    }

    protected String getAuthToken() {
        return authToken;
    }

    protected void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean register(String username, String password, String email) {
        return http.register(username, password, email);
    }

    public boolean login(String username, String password) {
        return http.login(username, password);
    }

    public boolean logout() {
        return http.logout();
    }

    public int createGame(String gameName) {
        return http.createGame(gameName);
    }

    public HashSet<GameData> listGames() {
        return http.listGames();
    }

    public boolean joinGame(int gameId, String playerColor) {
        return http.joinGame(gameId, playerColor);
    }

    public void connectWS() {
        try {
            ws = new WebsocketCommunicator(serverDomain);
        } catch (Exception e) {
            System.out.println("Failed to make connection with server");
        }
    }

    public void closeWS() {
        try {
            ws.session.close();
            ws = null;
        } catch (IOException e) {
            System.out.println("Failed to close connection with server");
        }
    }

    public void sendWSMessage(String message) {
        ws.sendMessage(message);
    }

    public void sendCommand(UserGameCommand command) {
        String message = new Gson().toJson(command);
        ws.sendMessage(message);
    }

    public void joinPlayer(int gameID, ChessGame.TeamColor color) {
        sendCommand(new JoinPlayer(authToken, gameID, color));
    }

    public void joinObserver(int gameID) {
        sendCommand(new JoinObserver(authToken, gameID));
    }

    public void makeMove(int gameID, ChessMove move) {
        sendCommand(new MakeMove(authToken, gameID, move));
    }

    public void leave(int gameID) {
        sendCommand(new Leave(authToken, gameID));
    }

    public void resign(int gameID) {
        sendCommand(new Resign(authToken, gameID));
    }
}

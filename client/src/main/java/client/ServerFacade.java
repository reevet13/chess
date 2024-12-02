package client;

import chess.ChessGame;
import chess.ChessMove;
import model.GameData;

import com.google.gson.Gson;
import websocket.messages.ServerMessage;
import websocket.commands.*;

import java.io.IOException;
import java.util.*;

public class ServerFacade {

    HttpCommunicator http;
    String authToken;
    WebsocketCommunicator ws;
    String serverDomain;

    public ServerFacade() throws Exception {
        this("localhost:8080");
    }

    public ServerFacade(String serverDomain) throws Exception {
        this.serverDomain = serverDomain;
        http = new HttpCommunicator(this, serverDomain);
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

    /**
     * Connect to a game as either a player or observer.
     *
     * @param gameID    The ID of the game to connect to.
     * @param isObserver True if connecting as an observer, false if connecting as a player.
     * @param color      The color of the player (white/black) if joining as a player; null if observer.
     */
    public void connect(int gameID, boolean isObserver, String color) {
        sendCommand(new Connect(authToken, gameID, isObserver, color));
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


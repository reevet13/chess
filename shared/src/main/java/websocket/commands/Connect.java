package websocket.commands;

import chess.ChessGame;

public class Connect extends UserGameCommand {

    int gameID; // ID of the game the client is connecting to
    boolean isObserver; // Indicates if the client is joining as an observer

    public Connect(String authToken, int gameID, boolean isObserver) {
        super(authToken);
        this.commandType = CommandType.CONNECT; // Define CONNECT as a command type
        this.gameID = gameID;
        this.isObserver = isObserver;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isObserver() {
        return isObserver;
    }
}


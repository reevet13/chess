package websocket.commands;

import chess.ChessGame;

public class Connect extends UserGameCommand {

    private int gameID;
    private boolean isObserver;
    private ChessGame.TeamColor color; // Optional, for player-specific data

    public Connect(String authString, int gameID, boolean isObserver, ChessGame.TeamColor color) {
        super(authString);
        this.commandType = CommandType.CONNECT;
        this.gameID = gameID;
        this.isObserver = isObserver;
        this.color = color; // Can be null if joining as observer
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isObserver() {
        return isObserver;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }
}

package websocket.commands;

import chess.ChessGame;

public class Connect extends UserGameCommand {

    private int gameID;
 // Optional, for player-specific data

    public Connect(String authString, int gameID) {
        super(authString);
        this.commandType = CommandType.CONNECT;
        this.gameID = gameID;
// Can be null if joining as observer
    }

    public int getGameID() {
        return gameID;
    }


}

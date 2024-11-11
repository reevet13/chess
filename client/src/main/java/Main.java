import chess.*;
import client.ServerFacade;
import server.Server;
import ui.PreloginREPL;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        Server server = new Server();
        int port = server.run(0); // 0 means to use any available port
        System.out.println("Started server on port: " + port);

        // Initialize ServerFacade with the dynamic port
        ServerFacade serverFacade = new ServerFacade("http://localhost:" + port);

        PreloginREPL prelogin = new PreloginREPL(serverFacade);
        prelogin.run();
        System.out.println("Exited");

    }
}
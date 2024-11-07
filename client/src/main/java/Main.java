import chess.*;
import client.ServerFacade;
import ui.PreloginREPL;

public class Main {
    public static void main(String[] args) throws Exception{
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        ServerFacade server = new ServerFacade();

        PreloginREPL pre_login = new PreloginREPL(server);
        pre_login.run();
        System.out.println("Exited");
    }
}
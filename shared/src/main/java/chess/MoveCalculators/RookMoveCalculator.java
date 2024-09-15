package chess.MoveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator {
    public static Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> rookMoves = new ArrayList<ChessMove>();

        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            //if empty, add
            if (board.getPiece(endPosition) == null){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //right
        boardLimit = 8 - (startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            if (board.getPiece(endPosition) == null){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            if (board.getPiece(endPosition) == null){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            if (board.getPiece(endPosition) == null){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }

        return rookMoves;
    }
}

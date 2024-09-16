package chess.moveCalculators;

import chess.*;
import java.util.Collection;
import java.util.ArrayList;

public class RookMoveCalculator {
    public static Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> rookMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            if (board.getPiece(endPosition) == null){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            } else {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //right
        boardLimit = 8 - startPosition.getColumn();
        for (int i = 1; i <= boardLimit; i++) {
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            } else {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            } else {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            } else {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }

        return rookMoves;
    }
}

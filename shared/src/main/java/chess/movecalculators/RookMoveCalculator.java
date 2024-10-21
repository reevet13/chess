package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator {
    public static Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> rookMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            //check if free
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //right
        boardLimit = 8 - startPosition.getColumn();
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            //check if free
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            //check if free
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            //check if free
            if (board.getPiece(endPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                rookMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }

        return rookMoves;
    }
}

package chess.MoveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator {
    public static Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> queenMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            //check if free
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //up and right
        boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            //check if free
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }
        //right
        boardLimit = 8 - startPosition.getColumn();
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            //check if free
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //down and right
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
            //check if free
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            //check if free
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //down and left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
            //check if free
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            //check if free
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            }
        }
        //up and left
        boardLimit = 8 - Math.max(startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
            //check if free
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }

        return queenMoves;
    }
}

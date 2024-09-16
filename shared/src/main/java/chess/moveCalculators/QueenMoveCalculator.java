package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator {
    public static Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> queenMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //right
        boardLimit = 8 - startPosition.getColumn();
        for (int i = 1; i <= boardLimit; i++) {
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()) {
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }

        //up and right
        boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down and right
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down and left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //up and left
        boardLimit = 8 - Math.max(startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
            if (board.getPiece(endPosition) == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            } else {
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }

        return queenMoves;
    }
}

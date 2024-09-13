package chess.MoveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator {
    public static Collection<ChessMove> getQueenMoves (ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> queenMoves = new ArrayList<ChessMove>();
        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //up right diagonal
        boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //right
        boardLimit = 8 - (startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        // down and to the right ( - row + col)
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() + i);
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //down and to the left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() - i);
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        //up and to the left
        boardLimit = 8 - Math.max(startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
            //if empty, add
            if (board.getPiece(endPosition) == null){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                //if they're the same team, stop
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
                //if they're not the same team, you can go there and then stop
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                queenMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
            }
        }
        return queenMoves;
    }
}

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
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up right diagonal
        boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //right
        boardLimit = 8 - (startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        // down and to the right ( - row + col)
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() + i);
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and to the left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() - i);
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and to the left
        boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
            queenMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        return queenMoves;
    }
}

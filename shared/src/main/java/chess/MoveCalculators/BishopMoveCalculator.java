package chess.MoveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class BishopMoveCalculator {
    public static Collection<ChessMove> getBishopMoves (ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        // calculates up and to the right
        // max calculates which is greater between the row and the column
        // //8 - max says how many total spaces are available to move (in the shortest direction)
        int boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() + i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        // down and to the right ( - row + col)
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() + i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and to the left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() - i, startPosition.getColumn() - i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and to the left
        boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            ChessPosition endPosition = new ChessPosition (startPosition.getRow() + i, startPosition.getColumn() - i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }

        return bishopMoves;
    }
}

package chess.MoveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoveCalculator {
    public static Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> knightMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;
        //up up right
        if (startPosition.getRow() <= 6 && startPosition.getColumn() <= 7) {
            endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up right right
        if (startPosition.getRow() <= 7 && startPosition.getColumn() <= 6) {
            endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down right right
        if (startPosition.getRow() >= 2 && startPosition.getColumn() <= 6) {
            endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 2);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down down right
        if (startPosition.getRow() >= 3 && startPosition.getColumn() <= 7) {
            endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down down left
        if (startPosition.getRow() >= 3 && startPosition.getColumn() >= 2) {
            endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() - 1);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down left left
        if (startPosition.getRow() >= 2 && startPosition.getColumn() >= 3) {
            endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up left left
        if (startPosition.getRow() <= 7 && startPosition.getColumn() >= 3) {
            endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 2);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up up left
        if (startPosition.getRow() <= 6 && startPosition.getColumn() >= 2) {
            endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        return knightMoves;
    }
}

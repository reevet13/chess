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
        //up up right
        ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);
        if (startPosition.getRow() <= 6 && startPosition.getColumn() <= 7) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up right right
        endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);
        if (endPosition.getRow() <= 8 && endPosition.getColumn() <= 8) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down right right
        endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 2);
        if (endPosition.getRow() > 0 && endPosition.getColumn() <= 8) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down down right
        endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);
        if (endPosition.getRow() > 0 && endPosition.getColumn() <= 8) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down down left
        endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() - 1);
        if (endPosition.getRow() > 0 && endPosition.getColumn() > 0) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down left left
        endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);
        if (endPosition.getRow() > 0 && endPosition.getColumn() > 0) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up left left
        endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 2);
        if (endPosition.getRow() <= 8 && endPosition.getColumn() > 0) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up up left
        endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);
        if (endPosition.getRow() <= 8 && endPosition.getColumn() > 0) {
            knightMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        return knightMoves;
    }
}

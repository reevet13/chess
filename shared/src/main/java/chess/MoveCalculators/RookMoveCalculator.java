package chess.MoveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator {
    public static Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> rookMoves = new ArrayList<ChessMove>();
        //up
        int boardLimit = 8 - startPosition.getRow();
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
            rookMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //right
        boardLimit = 8 - (startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
            rookMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down
        boardLimit = 8 - (9 - startPosition.getRow());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
            rookMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //left
        boardLimit = 8 - (9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++) {
            ChessPosition endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
            rookMoves.add(new ChessMove(startPosition, endPosition, null));
        }

        return rookMoves;
    }
}

package chess.MoveCalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalculator {
    public static Collection<ChessMove> getKingMoves (ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> kingMoves = new ArrayList<ChessMove>();
        //up
        ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
        if (endPosition.getRow() <= 8){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and right
        endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
        if (endPosition.getRow() <= 8 && endPosition.getColumn() <= 8){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //right
        endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1);
        if (endPosition.getColumn() <= 8){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and right
        endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
        if (endPosition.getRow() > 0 && endPosition.getColumn() <= 8){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down
        endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
        if (endPosition.getRow() > 0){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and left
        endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
        if (endPosition.getRow() > 0 && endPosition.getColumn() > 0){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //left
        endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 1);
        if (endPosition.getColumn() > 0){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and left
        endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
        if (endPosition.getRow() <= 8 && endPosition.getColumn() > 0){
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }

        return kingMoves;
    }
}

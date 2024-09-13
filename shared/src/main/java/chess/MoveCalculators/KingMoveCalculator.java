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
        ChessPosition endPosition;
        if (startPosition.getRow() <= 7){
            endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and right
        if (startPosition.getRow() <= 7 && startPosition.getColumn() <= 7){
            endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //right
        if (startPosition.getColumn() <= 7){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1);
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and right
        if (startPosition.getRow() >= 2 && startPosition.getColumn() <= 7){
            endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down
        if (startPosition.getRow() >= 2){
            endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and left
        if (startPosition.getRow() >= 2 && startPosition.getColumn() >= 2){
            endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //left
        if (startPosition.getColumn() >= 2){
            endPosition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 1);
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and left
        if (startPosition.getRow() <= 7 && startPosition.getColumn() >= 2){
            endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            kingMoves.add(new ChessMove(startPosition, endPosition, null));
        }

        return kingMoves;
    }
}

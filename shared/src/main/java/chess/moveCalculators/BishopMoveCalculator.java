package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMoveCalculator {

    public static Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //up and right
        int boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and right
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //down and left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        //up and left
        boardLimit = 8 - Math.max(startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
            bishopMoves.add(new ChessMove(startPosition, endPosition, null));
        }
        return bishopMoves;
    }

}

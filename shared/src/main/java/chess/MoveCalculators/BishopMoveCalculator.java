package chess.MoveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator {
    public static Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //up and right
        int boardLimit = 8 - Math.max(startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
            //check if free
            if (board.getPiece(endPosition) == null){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }
        //down and right
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
            //check if free
            if (board.getPiece(endPosition) == null){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }
        //down and left
        boardLimit = 8 - Math.max(9 - startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
            //check if free
            if (board.getPiece(endPosition) == null){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }
        //up and left
        boardLimit = 8 - Math.max(startPosition.getRow(), 9 - startPosition.getColumn());
        for (int i = 1; i <= boardLimit; i++){
            endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
            //check if free
            if (board.getPiece(endPosition) == null){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                //capture
            } else if (board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                bishopMoves.add(new ChessMove(startPosition, endPosition, null));
                break;
                //blocked
            } else if (board.getPiece(endPosition).getTeamColor() == board.getPiece(startPosition).getTeamColor()){
                break;
            }
        }

        return bishopMoves;
    }
}

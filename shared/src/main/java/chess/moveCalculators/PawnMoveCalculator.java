package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;


public class PawnMoveCalculator {
    public static Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition startPosition){
        Collection<ChessMove> pawnMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //WHITE
        if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE){

            //start move
            if (startPosition.getRow() == 2){
                ChessPosition checkFirstSpot = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
                //make sure nobody is in the way
                if (board.getPiece(checkFirstSpot) == null && board.getPiece(endPosition) == null){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            //move and capture (not promote)
            if (startPosition.getRow() <= 7) {
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                //potential promotion
                if (endPosition.getRow() == 8 && board.getPiece(endPosition) == null){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                } else if (board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
                //capture right
                if (startPosition.getColumn() <= 7){
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                    //promotion?
                    if (endPosition.getRow() == 8 && board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    } else if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
                //capture left
                if (startPosition.getColumn() >= 2){
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                    //promotion?
                    if (endPosition.getRow() == 8 && board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    } else if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
            }
            //BLACK
        } else if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            if (startPosition.getRow() == 7){
                ChessPosition checkFirstSpot = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
                //make sure nobody is in the way
                if (board.getPiece(checkFirstSpot) == null && board.getPiece(endPosition) == null){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            if (startPosition.getRow() >= 2) {
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                //potential promotion
                if (endPosition.getRow() == 1 && board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                } else if (board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            //capture down right
            if (startPosition.getColumn() <= 7){
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
                //promotion?
                if (endPosition.getRow() == 1 && board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                } else if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            if (startPosition.getColumn() >= 2){
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
                //promotion?
                if (endPosition.getRow() == 1 && board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                } else if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
        }

        return pawnMoves;
    }
}

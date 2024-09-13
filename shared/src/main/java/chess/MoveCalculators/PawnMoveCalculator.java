package chess.MoveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator {
    public static Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> pawnMoves = new ArrayList<ChessMove>();
        ChessPiece pawn = board.getPiece(startPosition);
        ChessGame.TeamColor teamColor = pawn.getTeamColor();
        ChessPosition endPosition;
        ChessPosition blockDouble;
        //white team!
        if (teamColor == ChessGame.TeamColor.WHITE){
            //initial double move
            if (startPosition.getRow() == 2){
                endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
                blockDouble = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null && board.getPiece(blockDouble) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            //all forward moves
            if (startPosition.getRow() <= 6){
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }                //capture up and right
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
                //capture up and left
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
            }
            //promotion
            if (startPosition.getRow() == 7){
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                //capture up and right
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    }
                }
                //capture up and left
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    }
                }
            }

        //black team!!
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            //initial double move
            if (startPosition.getRow() == 7) {
                endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
                blockDouble = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null && board.getPiece(blockDouble) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            //all forward moves
            if (startPosition.getRow() >= 3) {
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }                //capture down and right
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
                //capture down and left
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
            }
            //promotion
            if (startPosition.getRow() == 2){
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                //capture down and right
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    }
                }
                //capture down and left
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
        }
        return pawnMoves;
    }
}

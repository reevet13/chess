package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator {
    public static Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> pawnMoves = new ArrayList<ChessMove>();
        ChessPosition endPosition;

        //WHITE
        if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            //start move
            if (startPosition.getRow() == 2) {
                endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
                ChessPosition checkFirstBlocked = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                if (board.getPiece(checkFirstBlocked) == null && board.getPiece(endPosition) == null){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            //normal move
            }
            if (startPosition.getRow() <= 6){
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
                //up and right capture
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
                //up and left capture
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
            //potential promotion
            } else if (startPosition.getRow() == 7){
                //normal move
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null){
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                }
                //up and right capture
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    }
                }
                //up and left capture
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    }
                }
            }

        }
        //BLACK
        else if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            //start move
            if (startPosition.getRow() == 7) {
                endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
                ChessPosition checkFirstBlocked = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                if (board.getPiece(checkFirstBlocked) == null && board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
                //normal down
            }
            if (startPosition.getRow() >= 3) {
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                }
                //down and right capture
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
                //down and left capture
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, null));
                    }
                }
                //potential promotion
            } else if (startPosition.getRow() == 2) {
                //normal down
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                if (board.getPiece(endPosition) == null) {
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                }
                //down and right capture
                if (startPosition.getColumn() <= 7) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    }
                }
                //down and left capture
                if (startPosition.getColumn() >= 2) {
                    endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
                    if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    }
                }
            }
        }

        return pawnMoves;
    }
}

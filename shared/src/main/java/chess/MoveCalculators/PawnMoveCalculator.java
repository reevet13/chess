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
        //white team!
        if (teamColor == ChessGame.TeamColor.WHITE){
            //initial double move
            if (startPosition.getRow() == 2){
                endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, null));
            }
            //all forward moves
            if (startPosition.getRow() < 7){
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, null));
            }
            //promotion
            if (startPosition.getRow() == 7){
                endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
            }
        //black team!!
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            //initial double move
            if (startPosition.getRow() == 7) {
                endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, null));
            }
            //all forward moves
            if (startPosition.getRow() > 2) {
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, null));
            }
            //promotion
            if (startPosition.getRow() == 2){
                endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
            }
        }



        return pawnMoves;
    }
}

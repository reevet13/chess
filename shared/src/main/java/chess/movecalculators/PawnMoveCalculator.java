package chess.movecalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator {

    public static Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> pawnMoves = new ArrayList<>();

        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null || piece.getPieceType() != ChessPiece.PieceType.PAWN) {
            return pawnMoves; // If there's no pawn at the start position, return an empty list.
        }

        ChessGame.TeamColor color = piece.getTeamColor();
        int direction = color == ChessGame.TeamColor.WHITE ? 1 : -1; // White moves up (+1), Black moves down (-1)

        // Add standard moves and captures
        addStandardMoves(pawnMoves, board, startPosition, direction, color);
        addCaptureMoves(pawnMoves, board, startPosition, direction, color);

        return pawnMoves;
    }

    private static void addStandardMoves(Collection<ChessMove> pawnMoves, ChessBoard board,
                                         ChessPosition startPosition, int direction, ChessGame.TeamColor color) {
        int currentRow = startPosition.getRow();
        int nextRow = currentRow + direction;
        ChessPosition oneStep = new ChessPosition(nextRow, startPosition.getColumn());

        if (board.getPiece(oneStep) == null) {
            // Single step move
            if (isPromotionRow(nextRow, color)) {
                addPromotionMoves(pawnMoves, startPosition, oneStep);
            } else {
                pawnMoves.add(new ChessMove(startPosition, oneStep, null));
            }

            // Double step move if on starting row
            if (isStartingRow(currentRow, color)) {
                ChessPosition twoSteps = new ChessPosition(currentRow + (2 * direction), startPosition.getColumn());
                if (board.getPiece(twoSteps) == null) {
                    pawnMoves.add(new ChessMove(startPosition, twoSteps, null));
                }
            }
        }
    }

    private static void addCaptureMoves(Collection<ChessMove> pawnMoves, ChessBoard board,
                                        ChessPosition startPosition, int direction, ChessGame.TeamColor color) {
        int nextRow = startPosition.getRow() + direction;

        ChessPosition rightCapture = new ChessPosition(nextRow, startPosition.getColumn() + 1);
        ChessPosition leftCapture = new ChessPosition(nextRow, startPosition.getColumn() - 1);

        // Right capture
        if (isValidCapture(board, rightCapture, color)) {
            if (isPromotionRow(nextRow, color)) {
                addPromotionMoves(pawnMoves, startPosition, rightCapture);
            } else {
                pawnMoves.add(new ChessMove(startPosition, rightCapture, null));
            }
        }

        // Left capture
        if (isValidCapture(board, leftCapture, color)) {
            if (isPromotionRow(nextRow, color)) {
                addPromotionMoves(pawnMoves, startPosition, leftCapture);
            } else {
                pawnMoves.add(new ChessMove(startPosition, leftCapture, null));
            }
        }
    }

    private static boolean isValidCapture(ChessBoard board, ChessPosition endPosition, ChessGame.TeamColor color) {
        if (!isPositionWithinBounds(endPosition)) {
            return false;
        }
        ChessPiece endPiece = board.getPiece(endPosition);
        return endPiece != null && endPiece.getTeamColor() != color;
    }

    private static void addPromotionMoves(Collection<ChessMove> pawnMoves, ChessPosition startPosition,
                                          ChessPosition endPosition) {
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
        pawnMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
    }

    private static boolean isPromotionRow(int row, ChessGame.TeamColor color) {
        return (color == ChessGame.TeamColor.WHITE && row == 8) || (color == ChessGame.TeamColor.BLACK && row == 1);
    }

    private static boolean isStartingRow(int row, ChessGame.TeamColor color) {
        return (color == ChessGame.TeamColor.WHITE && row == 2) || (color == ChessGame.TeamColor.BLACK && row == 7);
    }

    private static boolean isPositionWithinBounds(ChessPosition position) {
        int row = position.getRow();
        int column = position.getColumn();
        return row >= 1 && row <= 8 && column >= 1 && column <= 8;
    }

}

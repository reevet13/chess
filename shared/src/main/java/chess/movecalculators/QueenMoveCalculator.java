package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator {
    public static Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> queenMoves = new ArrayList<>();

        // Define directions for the queen (combinations of row and column shifts)
        int[][] directions = {
                {1, 0},  // up
                {1, 1},  // up and right
                {0, 1},  // right
                {-1, 1}, // down and right
                {-1, 0}, // down
                {-1, -1},// down and left
                {0, -1}, // left
                {1, -1}  // up and left
        };

        // For each direction, add valid moves
        for (int[] direction : directions) {
            addMovesInDirection(queenMoves, board, startPosition, direction[0], direction[1]);
        }

        return queenMoves;
    }

    // Helper method to add moves in a straight line or diagonal based on the direction
    private static void addMovesInDirection(Collection<ChessMove> queenMoves, ChessBoard board,
                                            ChessPosition startPosition, int rowShift, int colShift) {
        ChessPosition endPosition;
        int currentRow = startPosition.getRow();
        int currentCol = startPosition.getColumn();
        int boardLimit = 8; // Chess board has 8 rows and columns

        while (true) {
            currentRow += rowShift;
            currentCol += colShift;

            // Stop if we're off the board
            if (!isPositionWithinBounds(currentRow, currentCol)) {
                break;
            }

            endPosition = new ChessPosition(currentRow, currentCol);
            ChessPiece endPiece = board.getPiece(endPosition);

            // Check if the move is valid
            if (endPiece == null) {
                queenMoves.add(new ChessMove(startPosition, endPosition, null)); // Empty square
            } else {
                // Capture if the piece belongs to the opponent
                if (endPiece.getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                    queenMoves.add(new ChessMove(startPosition, endPosition, null)); // Capture move
                }
                break; // Stop if the path is blocked by any piece
            }
        }
    }

    // Helper method to check if a position is within the board's bounds
    private static boolean isPositionWithinBounds(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }
}

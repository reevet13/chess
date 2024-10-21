package chess;

import java.util.Collection;
import java.util.Objects;

import chess.moveCalculators.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (type){
            case BISHOP ->  BishopMoveCalculator.getBishopMoves(board, myPosition);
            case ROOK ->  RookMoveCalculator.getRookMoves(board, myPosition);
            case QUEEN ->  QueenMoveCalculator.getQueenMoves(board, myPosition);
            case KING ->  KingMoveCalculator.getKingMoves(board, myPosition);
            case KNIGHT ->  KnightMoveCalculator.getKnightMoves(board, myPosition);
            case PAWN ->  PawnMoveCalculator.getPawnMoves(board, myPosition);
        };

    }

    @Override
    public String toString() {
        return String.format("color: %s, type: %s", pieceColor, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}

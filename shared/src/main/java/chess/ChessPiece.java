package chess;

//import chess.MoveCalculators.*;

import java.util.ArrayList;
import java.util.Collection;

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
     * I can call these by saying PieceType.King etc.
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
        return new ArrayList<>();
        //return switch (type) {
            //case BISHOP -> BishopMoveCalculator.typeMoves(board, myPosition);

        };
            /*case KING -> KingMoveCalculator.typeMoves(board, myPosition);
            case QUEEN -> QueenMoveCalculator.typeMoves(board, myPosition);
            case BISHOP -> BishopMoveCalculator.typeMoves(board, myPosition);
            case KNIGHT -> KnightMoveCalculator.typeMoves(board, myPosition);
            case ROOK -> RookMoveCalculator.typeMoves(board, myPosition);
            case PAWN -> PawnMoveCalculator.typeMoves(board, myPosition);
        */
}

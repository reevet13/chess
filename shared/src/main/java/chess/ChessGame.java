package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        setTeamTurn(TeamColor.WHITE);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece currentPiece = board.getPiece(startPosition);
        if (currentPiece == null){
            return null;
        }

        Collection<ChessMove> potentialMoves = new ArrayList<ChessMove>(currentPiece.pieceMoves(board, startPosition));
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>(potentialMoves.size());
            for (ChessMove move : potentialMoves){
                ChessPiece newPiece = board.getPiece(move.getEndPosition());
                board.addPiece(startPosition, null);
                board.addPiece(move.getEndPosition(), currentPiece);
                if (!isInCheck(currentPiece.getTeamColor())) {
                    validMoves.add(move);
                }
                board.addPiece(move.getEndPosition(), newPiece);
                board.addPiece(startPosition, currentPiece);
            }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> possibleMoves = validMoves(move.getStartPosition());
        boolean correctTurn = getTeamTurn() == board.getTeamOfPiece(move.getStartPosition());
        if (possibleMoves == null){
            throw new InvalidMoveException("No valid moves available");
        }
        boolean isGoodMove = possibleMoves.contains(move);
        if (isGoodMove && correctTurn){
            ChessPiece movingPiece = board.getPiece(move.getStartPosition());
            if (move.getPromotionPiece() != null){
                movingPiece = new ChessPiece(movingPiece.getTeamColor(), move.getPromotionPiece());
            }

            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), movingPiece);
            setTeamTurn(getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
        }
        else {
            throw new InvalidMoveException(String.format("Is the move valid? %b Is it your turn? %b", isGoodMove, correctTurn));
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //find king
        ChessPosition kingPosition = null;
        for (int row = 1; row <= 8 && kingPosition == null; row++){
            for (int col = 1; col <= 8 && kingPosition == null; col++){
                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
                if (currentPiece == null){
                    continue;
                }
                if (currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING){
                    kingPosition = new ChessPosition(row, col);
                }
            }
        }
        //check if king is in danger
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
                if (currentPiece == null || currentPiece.getTeamColor() == teamColor){
                    continue;
                }
                for (ChessMove enemyMove : currentPiece.pieceMoves(board, new ChessPosition(row, col))){
                    if (enemyMove.getEndPosition().equals(kingPosition)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && !hasValidMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && !hasValidMoves(teamColor);
    }

    public boolean hasValidMoves(TeamColor teamColor){
        for (int row = 1; row <= 8; row++){
            for (int col = 1; col <= 8; col ++){
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                Collection<ChessMove> moves;
                if (currentPiece != null && teamColor == currentPiece.getTeamColor()){
                    moves = validMoves(currentPosition);
                    if (!moves.isEmpty()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}

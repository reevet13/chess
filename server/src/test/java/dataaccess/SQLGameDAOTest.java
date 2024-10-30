package dataaccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLGameDAOTest {

    GameDAO gameDAO;
    GameData testData;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        gameDAO = new SQLGameDAO();
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("TRUNCATE game")) {
                statement.executeUpdate();
            }
        }
        ChessGame testGame = new ChessGame();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        testGame.setBoard(board);
        testData = new GameData(1234, "white", "black", "gameName", testGame);
    }

    @AfterEach
    void tearDown() throws SQLException, DataAccessException {
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("TRUNCATE game")) {
                statement.executeUpdate();
            }
        }
    }

    @Test
    void createGamePositive() throws DataAccessException, SQLException {
        gameDAO.createGame(testData);
        GameData resultData;

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, " +
                    "chessGame FROM game WHERE gameID=?")) {
                statement.setInt(1, testData.gameID());
                try (var results = statement.executeQuery()) {
                    results.next();
                    var gameID = results.getInt(("gameID"));
                    var whiteUsername = results.getString("whiteUsername");
                    var blackUsername = results.getString("blackUsername");
                    var gameName = results.getString("gameName");
                    var chessGame = deserializeGame(results.getString("chessGame"));
                    assertEquals(testData.game(), chessGame, "Game board is not equal");
                    resultData = new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
                }
            }
        }
        assertEquals(testData, resultData);
    }


    private ChessGame deserializeGame(String serializedGame) {
        return new Gson().fromJson(serializedGame, ChessGame.class);
    }
}

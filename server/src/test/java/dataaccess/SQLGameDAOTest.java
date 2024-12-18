package dataaccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

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
                statement.setInt(1, testData.getGameID());
                try (var results = statement.executeQuery()) {
                    results.next();
                    var gameID = results.getInt(("gameID"));
                    var whiteUsername = results.getString("whiteUsername");
                    var blackUsername = results.getString("blackUsername");
                    var gameName = results.getString("gameName");
                    var chessGame = deserializeGame(results.getString("chessGame"));
                    assertEquals(testData.getGame(), chessGame, "Game board is not equal");
                    resultData = new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
                }
            }
        }
        assertEquals(testData, resultData);
    }

    @Test
    void createGameNegative() throws DataAccessException {
        gameDAO.createGame(testData);
        assertThrows(DataAccessException.class, () -> gameDAO.createGame(testData));
    }

    @Test
    void listGamesPositive () throws DataAccessException, SQLException {
        gameDAO.createGame(testData);
        gameDAO.createGame(new GameData(4321, "white", "black", "gameName",
                new ChessGame()));

        HashSet<GameData> resultGames = gameDAO.listGames();

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, " +
                    "chessGame FROM game")) {
                try (var results = statement.executeQuery()) {
                    int i = 0;
                    while (results.next()) { i++; }
                    assertEquals(i, resultGames.size(), "Incorrect game count");
                }
            }
        }
    }

    @Test
    void listGamesNegative() {
        HashSet<GameData> games = gameDAO.listGames();
        assertEquals(0, games.size(), "Should be empty set");
    }

    @Test
    void getGamePositive() throws DataAccessException {
        gameDAO.createGame(testData);
        assertEquals(testData, gameDAO.getGame(testData.getGameID()));
    }

    @Test
    void getGameNegative() {
        assertThrows(DataAccessException.class, () -> gameDAO.getGame(testData.getGameID()));
    }

    @Test
    void gameExistsPositive() throws DataAccessException {
        gameDAO.createGame(testData);
        assertTrue(gameDAO.gameExists(testData.getGameID()));
    }

    @Test
    void gameExistsNegative() {
        assertFalse(gameDAO.gameExists(testData.getGameID()));
    }

    @Test
    void updateGamePositive() throws DataAccessException {
        gameDAO.createGame(testData);
        GameData resultingGame = new GameData(testData.getGameID(), "whiter", "black",
                "gameName", testData.getGame());
                gameDAO.updateGame(resultingGame);
                assertEquals(resultingGame, gameDAO.getGame(testData.getGameID()));
    }

    @Test
    void updateGameNegative() {
        assertThrows(DataAccessException.class, () -> gameDAO.updateGame(testData));
    }

    @Test
    void clear() throws DataAccessException, SQLException {
        gameDAO.createGame(testData);
        gameDAO.clear();

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT gameID FROM game WHERE gameID=?")) {
                statement.setInt(1, testData.getGameID());
                try (var results = statement.executeQuery()) {
                    assertFalse(results.next());
                }
            }
        }
    }


    private ChessGame deserializeGame(String serializedGame) {
        return new Gson().fromJson(serializedGame, ChessGame.class);
    }
}

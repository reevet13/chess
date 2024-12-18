package service;

import chess.ChessBoard;
import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.Service;

import java.util.HashSet;

public class ServiceTests {
    static Service service;
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;
    static AuthData authData;

    static UserData testUser;

    @BeforeAll
    static void init() {

        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        service = new Service(userDAO, authDAO, gameDAO);


    }

    @BeforeEach
    void setup() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();

        authData = new AuthData("Username", "authToken");
        authDAO.addAuth(authData);

        testUser = new UserData("username", "password", "email");
    }

    @Test
    @DisplayName("Valid Register")
    void createUserTestPass() throws BadRequestException, DataAccessException {
        AuthData auth = service.createUser(testUser);
        Assertions.assertEquals(authDAO.getAuth(auth.authToken()), auth);
    }

    @Test
    @DisplayName("Invalid Register")
    void createUserTestFail() throws BadRequestException {
        service.createUser(testUser);
        Assertions.assertThrows(BadRequestException.class, () -> service.createUser(testUser));
    }

    @Test
    @DisplayName("Proper Login User")
    void loginUserTestPass() throws BadRequestException, UnauthorizedException, DataAccessException {
        service.createUser(testUser);
        AuthData authData = service.loginUser(testUser);
        Assertions.assertEquals(authDAO.getAuth(authData.authToken()), authData);
    }

    @Test
    @DisplayName("Improper Login User")
    void loginUserTestFail() throws BadRequestException {
        Assertions.assertThrows(UnauthorizedException.class, () -> service.loginUser(testUser));
        service.createUser(testUser);
        UserData badPass = new UserData(testUser.username(), "wrong", testUser.email());
        Assertions.assertThrows(UnauthorizedException.class, () -> service.loginUser(badPass));
    }

    @Test
    @DisplayName("Proper Logout User")
    void logoutUserTestPositive() throws BadRequestException, UnauthorizedException {
        AuthData auth = service.createUser(testUser);
        service.logoutUser(auth.authToken());
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Improper Logout User")
    void logoutUserTestNegative() throws BadRequestException {
        AuthData auth = service.createUser(testUser);
        Assertions.assertThrows(UnauthorizedException.class, () -> service.logoutUser("badAuthToken"));
    }

    @Test
    @DisplayName("Proper Clear DB")
    void clearTestPositive() throws BadRequestException {
        AuthData auth = service.createUser(testUser);
        service.clear();
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.getUser(testUser.username()));
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Improper Clear DB")
    void clearTestNegative() throws BadRequestException {
        Assertions.assertDoesNotThrow(() -> service.clear());
    }

    @Test
    @DisplayName("Create Valid Game")
    void createGameTestPositive() throws UnauthorizedException, BadRequestException {
        int gameID = service.createGame(authData.authToken(), "name1");
        Assertions.assertTrue(gameDAO.gameExists(gameID), "Game ID 1 should exist after creation");

        int gameID2 = service.createGame(authData.authToken(), "name2");
        Assertions.assertTrue(gameDAO.gameExists(gameID2), "Game ID 2 should exist after creation");

        Assertions.assertNotEquals(gameID, gameID2, "Game IDs should not be the same for two different games");
    }


    @Test
    @DisplayName("Create Invalid Game")
    void createGameTestNegative() throws UnauthorizedException {
        Assertions.assertThrows(UnauthorizedException.class, () -> service.createGame("badToken", "name"));
    }

    @Test
    @DisplayName("Proper List Games")
    void listGamesTestPositive() throws UnauthorizedException, BadRequestException {
        int gameID1 = service.createGame(authData.authToken(), "name");
        int gameID2 = service.createGame(authData.authToken(), "name");
        int gameID3 = service.createGame(authData.authToken(), "name");

        HashSet<GameData> expected = HashSet.newHashSet(8);
        ChessGame game = new ChessGame();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        game.setBoard(board);
        expected.add(new GameData(gameID1, null, null, "name", game));
        expected.add(new GameData(gameID2, null, null, "name", game));
        expected.add(new GameData(gameID3, null, null, "name", game));

        Assertions.assertEquals(expected, service.listGames(authData.authToken()));
    }

    @Test
    @DisplayName("Improper List Games")
    void listGamesTestNegative() {
        Assertions.assertThrows(UnauthorizedException.class, () -> service.listGames("badToken"));
    }

    @Test
    @DisplayName("Proper Join Game")
    void joinGameTestPositive() throws UnauthorizedException, BadRequestException, DataAccessException {
        int gameID = service.createGame(authData.authToken(), "name");

        service.joinGame(authData.authToken(), gameID, "WHITE");
        ChessGame game = new ChessGame();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        game.setBoard(board);

        GameData expectedGameData = new GameData(gameID, authData.username(), null, "name", game);

        Assertions.assertEquals(expectedGameData, gameDAO.getGame(gameID));
    }

    @Test
    @DisplayName("Improper Join Game")
    void joinGameTestNegative() throws UnauthorizedException, BadRequestException {
        int gameID = service.createGame(authData.authToken(), "name");
        Assertions.assertThrows(UnauthorizedException.class, () -> service.joinGame("badToken", gameID, "WHITE"));
        Assertions.assertThrows(BadRequestException.class, () -> service.joinGame(authData.authToken(), 11111, "WHITE"));
        Assertions.assertThrows(BadRequestException.class, () -> service.joinGame(authData.authToken(), gameID, "INVALID"));
    }
}


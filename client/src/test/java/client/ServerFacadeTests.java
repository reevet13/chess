package client;

import client.ServerFacade;
import exception.ResponseException;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;

    private ServerFacade facade;
    static int port;

    @BeforeAll
    public static void init() {
        server = new Server();
        port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @BeforeEach
    void setup() {
        server.clearDB();
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterEach
    void cleanup() {
        server.clearDB();
    }

    @Test
    public void registerPositive() {
        assertTrue(facade.register("username", "password", "email"));
    }

    @Test
    public void registerNegative() {
        facade.register("username", "password", "email");
        assertFalse(facade.register("username", "password", "email"));
    }

    @Test
    public void loginPositive() {
        facade.register("username", "password", "email");
        assertTrue(facade.login("username", "password"));
    }

    @Test
    public void loginNegative() {
        facade.register("username", "password", "email");
        assertFalse(facade.login("username", "pass"));
    }

    @Test
    public void logoutPositive() {
        facade.register("username", "password", "email");
        assertTrue(facade.logout());
    }

    @Test
    public void logoutNegative() {
        assertFalse(facade.logout());
    }

    @Test
    public void createGamePositive() {
        facade.register("username", "password", "email");
        assertTrue(facade.createGame("gameName") >= 0);
    }

    @Test
    public void createGameNegative() {
        assertEquals(-1, facade.createGame("gameName"));
    }

    @Test
    public void listGamesPositive(){
        // Assuming a game is created beforehand via the facade or a test setup
        facade.register("player1", "password", "email1");
        facade.createGame("TestGame");

        // Call listGames to retrieve games
        String result = facade.listGames();

        // Validate the response
        assertNotNull(result);
        assertTrue(result.contains("Game: TestGame"));
        assertTrue(result.contains("Players: []"));
    }


    @Test
    public void joinGamePositive() {
        facade.register("username", "password", "email");
        int id = facade.createGame("gameName");
        assertTrue(facade.joinGame(id, "WHITE"));
    }

    @Test
    public void joinGameNegative() {
        facade.register("username", "password", "email");
        int id = facade.createGame("gameName");
        facade.joinGame(id, "WHITE");
        assertTrue(facade.joinGame(id, "WHITE"));
    }

    @Test
    public void listGamesNegative() {
        String result = facade.listGames();

        // Step 3: Assert that the method returns the expected failure message
        assertNotNull(result);
        assertTrue(result.contains("Failed to retrieve games list."));  // Expected message on failure
    }



}
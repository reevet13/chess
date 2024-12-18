package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SQLAuthDAOTest {
    AuthDAO authDAO;
    AuthData testAuth;

    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        authDAO = new SQLAuthDAO();
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("TRUNCATE auth")) {
                statement.executeUpdate();
            }
        }
        testAuth = new AuthData("username", "token");
    }

    @AfterEach
    void tearDown() throws SQLException, DataAccessException {
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("TRUNCATE auth")) {
                statement.executeUpdate();
            }
        }
    }

    @Test
    void addAuthPositive() throws DataAccessException, SQLException {
        authDAO.addAuth(testAuth);
        String resultUsername;
        String resultToken;

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT username, authToken FROM auth WHERE username=?")) {
                statement.setString(1, testAuth.username());
                try (var results = statement.executeQuery()) {
                    results.next();
                    resultUsername = results.getString("username");
                    resultToken = results.getString("authToken");
                }
            }
        }
        assertEquals(testAuth, new AuthData(resultUsername, resultToken));
    }

    @Test
    void addAuthNegative() throws DataAccessException, SQLException {
        authDAO.addAuth(testAuth);
        authDAO.addAuth(testAuth);

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT username, authToken FROM auth WHERE username=?")) {
            statement.setString(1, testAuth.username());
                try (var results = statement.executeQuery()) {
                    results.next();
                    assertFalse(results.next()); //There should only be one element, despite having added two
                }
            }
        }
    }

    @Test
    void deleteAuthPositive() throws DataAccessException, SQLException {
        authDAO.addAuth(testAuth);
        authDAO.deleteAuth(testAuth.authToken());

        assertFalse(isAuthTokenPresent(testAuth.username())); // Checks that duplicate was not added

    }

    @Test
    void deleteAuthNegative(){
        assertDoesNotThrow(() -> authDAO.deleteAuth("fakeToken"));
    }

    @Test
    void getAuthPositive() throws DataAccessException {
        authDAO.addAuth(testAuth);
        AuthData result = authDAO.getAuth(testAuth.authToken());
        assertEquals(testAuth, result);
    }

    @Test
    void getAuthNegative() {
        authDAO.addAuth(testAuth);
        assertThrows(DataAccessException.class, () -> authDAO.getAuth("fakeToken"));
    }

    @Test
    void clear() throws DataAccessException, SQLException {
        authDAO.addAuth(testAuth);
        authDAO.clear();

        assertFalse(isAuthTokenPresent(testAuth.username())); // Checks that duplicate was not added

    }

    private boolean isAuthTokenPresent(String username) throws SQLException, DataAccessException {
        try (var con = DatabaseManager.getConnection();
             var statement = con.prepareStatement("SELECT username, authToken FROM auth WHERE username=?")) {
            statement.setString(1, username);
            try (var results = statement.executeQuery()) {
                return results.next(); // Returns true if a result is found
            }
        }
    }

}

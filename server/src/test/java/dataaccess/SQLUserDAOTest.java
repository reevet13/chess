package dataaccess;

import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SQLUserDAOTest {
    UserDAO userDAO;
    UserData testUser;

    @BeforeEach
    void setUo() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        userDAO = new SQLUserDAO();
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("TRUNCATE user")) {
                statement.executeUpdate();
            }
        }
        testUser = new UserData("username", "password", "email");
    }

    @AfterEach
    void tearDown() throws SQLException, DataAccessException {
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("TRUNCATE user")) {
                statement.executeUpdate();
            }
        }
    }

    @Test
    void createUserPositive() throws DataAccessException, SQLException {
        userDAO.createUser(testUser);
        String resultUsername;
        String resultPassword;
        String resultEmail;

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT username, password, email FROM user " +
                    "WHERE username=?")) {
                statement.setString(1, testUser.username());
                try (var results = statement.executeQuery()) {
                    results.next();
                    resultUsername = results.getString("username");
                    resultPassword = results.getString("password");
                    resultEmail = results.getString("email");
                }
            }
        }

        assertEquals(testUser.username(), resultUsername);
        assertTrue(BCrypt.checkpw(testUser.password(), resultPassword));
        assertEquals(testUser.email(), resultEmail);
    }

    @Test
    void createUserNegative() throws DataAccessException {
        userDAO.createUser(testUser);
        assertThrows(DataAccessException.class, () -> userDAO.createUser(testUser));
    }

    @Test
    void getUserPositive() throws DataAccessException {
        userDAO.createUser(testUser);
        UserData resultUser = userDAO.getUser(testUser.username());

        assertEquals(testUser.username(), resultUser.username());
        assertTrue(BCrypt.checkpw(testUser.password(), resultUser.password()));
        assertEquals(testUser.email(), resultUser.email());
    }

    @Test
    void getUserNegative() {
        assertThrows(DataAccessException.class, () -> userDAO.getUser(testUser.username()));
    }

    @Test
    void authenticateUserPositive() throws DataAccessException {
        userDAO.createUser(testUser);
        assertTrue(userDAO.authenticateUser(testUser.username(), testUser.password()));
    }

    @Test
    void authenticateUserNegative() throws DataAccessException {
        userDAO.createUser(testUser);
        assertFalse(userDAO.authenticateUser(testUser.username(), "wrong password"));
    }

    @Test
    void clear() throws DataAccessException, SQLException {
        userDAO.createUser(testUser);
        userDAO.clear();

        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT username, password, email FROM user " +
                    "WHERE username=?")) {
                statement.setString(1, testUser.username());
                try (var results = statement.executeQuery()) {
                    assertFalse(results.next());
                }
            }
        }
    }
}

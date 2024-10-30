package dataaccess;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
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
}

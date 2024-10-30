/*package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{

    public SQLUserDAO() {
        try { DatabaseManager.createDatabase(); } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try (var con = DatabaseManager.getConnection()) {
            var createTestTable = """
                    CREATE TABLE if NOT EXISTS user (
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255),
                        PRIMARY KEY (username))""";
            try (var createTableStatement = con.prepareStatement(createTestTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {throw new RuntimeException(e);}
    }

    @Override UserData getUser(String username) throws DataAccessException {
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT username, " +
                    "password, email FROM user WHERE username =?")) {
                statement.setString(1, username);
                try (var results = statement.executeQuery()) {
                    results.next();
                    var password = results.getString("password");
                    var email = results.getString("email");
                    return new UserData(username, password, email);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("User not found: " + username);
        }
    }
}*/

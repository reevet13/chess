package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO{

    public SQLAuthDAO() {
        try { DatabaseManager.createDatabase(); } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try (var con = DatabaseManager.getConnection()) {
            var createTestTable = """
                    CREATE TABLE if NOT EXISTS auth (
                                    username VARCHAR(255) NOT NULL,
                                    authToken VARCHAR(255) NOT NULL,
                                    PRIMARY KEY (authToken)
                                    )""";
            try (var createTableStatement = con.prepareStatement(createTestTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAuth(AuthData authData) {
        try (var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("INSERT INTO auth (username, authToken) VALUES(?, ?)")) {
                statement.setString(1, authData.username());
                statement.setString(2, authData.authToken());
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {}
    }
}

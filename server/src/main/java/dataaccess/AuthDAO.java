package dataAccess;

import model.AuthData;

public interface AuthDAO {
    void addAuth(AuthData authData);
    void clear();
    AuthData getAuth(String authToken) throws DataAccessException;
}

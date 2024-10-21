package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void addAuth(AuthData authData);
    void deleteAuth(String authToken);
    void clear();
    AuthData getAuth(String authToken) throws DataAccessException;
}

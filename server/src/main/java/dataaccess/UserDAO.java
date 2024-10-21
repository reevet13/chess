package dataaccess;

import model.UserData;

public interface UserDAO {

    UserData getUser(String username) throws DataAccessException;
    void createUser(UserData user) throws DataAccessException;
    boolean authenticateUser(UserData userData) throws DataAccessException;
    void clear();
}

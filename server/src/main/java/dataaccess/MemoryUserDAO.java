package dataAccess;

import model.UserData;

import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{

    private HashSet<UserData> db = HashSet.newHashSet(16);

    @Override
    public UserData getUser(String username) throws DataAccessException{
        for (UserData user : db) {
            if (user.username().equals(username)){
                return user;
            }
        }
        throw new DataAccessException("User not found" + username);
    }

    @Override
    public void createUser(UserData user) throws DataAccessException{
        try {
            getUser(user.username());
        }
        catch (DataAccessException exception) {
            db.add(user);
            return;
        }

        throw new DataAccessException("User already exists: " + user.username());
    }

    @Override
    public boolean authenticateUser(UserData userData) throws DataAccessException {
        String username = userData.username();
        String password = userData.password();
        boolean exists = false;
        for (UserData user : db) {
            if (user.username().equals(username)) {
                exists = true;
            }
            if (user.username().equals(username) && user.password().equals(password)) {
                return true;
            }
        }
        if (exists) {
            return false;
        }
        else {
            throw new DataAccessException("User does not exist: " + username);
        }
    }

    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }
}

package dataAccess;

import model.AuthData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{
    HashSet<AuthData> db = HashSet.newHashSet(16);;

    @Override
    public void addAuth(AuthData authData){
        db.add(authData);
    }

    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException{
        for (AuthData authData : db) {
            if (authData.authToken().equals(authToken)) {
                return authData;
            }
        }
        throw new DataAccessException("Auth Token does not exist: " + authToken);
    }

    @Override
    public void deleteAuth(String authToken){
        for (AuthData authData : db) {
            if (authData.authToken().equals(authToken)){
                db.remove(authData);
                break;
            }
        }
    }
}

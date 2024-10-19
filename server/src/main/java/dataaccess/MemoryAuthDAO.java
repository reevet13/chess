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
}

package dataAccess;

import model.GameData;

import javax.xml.crypto.Data;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    HashSet<GameData> db;

    public MemoryGameDAO(){
        db = HashSet.newHashSet(16);
    }

    @Override
    public void createGame(GameData game){
        db.add(game);
    }

    @Override
    public boolean gameExists(int gameID){
        for (GameData game : db){
            if(game.gameID() == gameID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData game : db){
            if(game.gameID() == gameID){
                return game;
            }
        }
        throw new DataAccessException("No game found with id: " + gameID);
    }

    @Override
    public void updateGame(GameData game) {
        try {
            db.remove(getGame(game.gameID()));
            db.add(game);
        } catch (DataAccessException e) {
            db.add(game);
        }
    }
}

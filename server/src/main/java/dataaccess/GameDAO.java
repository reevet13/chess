package dataAccess;

import model.GameData;

import java.util.HashSet;

public interface GameDAO {
    void createGame(GameData game) throws DataAccessException;
    boolean gameExists(int gameID);
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;
    HashSet<GameData> listGames();
    void clear();
}

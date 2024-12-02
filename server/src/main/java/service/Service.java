package service;

import chess.*;
import model.GameData;
import model.UserData;
import model.AuthData;
import dataaccess.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Service {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public Service(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public AuthData createUser(UserData userData) throws BadRequestException{

        try {
            userDAO.createUser(userData);
            return authTokenGenerator(userData);
        } catch (DataAccessException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    public AuthData loginUser(UserData userData) throws UnauthorizedException {
        try {
            if (userDAO.authenticateUser(userData.username(), userData.password())) {
                return authTokenGenerator(userData); // Reusing the helper method
            } else {
                throw new UnauthorizedException();
            }
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
    }

    public void logoutUser(String authToken) throws UnauthorizedException{
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
        authDAO.deleteAuth(authToken);
    }

    public void updateGame(String authToken, GameData gameData) throws UnauthorizedException, BadRequestException {
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }

        try {
            gameDAO.updateGame(gameData);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public int createGame(String authToken, String gameName) throws UnauthorizedException, BadRequestException {
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }

        int gameID;
        do { // Get random gameIDs until the gameID is not already in use
            gameID = ThreadLocalRandom.current().nextInt(1, 10000);
        } while (gameDAO.gameExists(gameID));

        try {
            ChessGame game = new ChessGame();
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            game.setBoard(board);
            gameDAO.createGame(new GameData(gameID, null, null, gameName, game));
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }

        return gameID;
    }

    public boolean joinGame(String authToken, int gameID, String color) throws UnauthorizedException, BadRequestException {
        AuthData authData;
        GameData gameData;
        try {
            authData = authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }

        try {
            gameData = gameDAO.getGame(gameID);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }

        String whiteUser = gameData.getWhiteUsername();
        String blackUser = gameData.getBlackUsername();

        if (Objects.equals(color, "WHITE")) {
            if (whiteUser != null && !whiteUser.equals(authData.username())){
                return false; // Spot taken by someone else
            } else {
                whiteUser = authData.username();
            }
        } else if (Objects.equals(color, "BLACK")) {
            if (blackUser != null && !blackUser.equals(authData.username())){
                return false; // Spot taken by someone else
            } else {
                blackUser = authData.username();
            }
        } else if (color != null) throw new BadRequestException("%s is not a valid team color".formatted(color));

        try {
            gameDAO.updateGame(new GameData(gameID, whiteUser, blackUser, gameData.getGameName(), gameData.getGame()));
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
        return true;
    }

    public HashSet<GameData> listGames(String authToken) throws UnauthorizedException {
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e){
            throw new UnauthorizedException();
        }
        return gameDAO.listGames();
    }

    public GameData getGameData(String authToken, int gameID) throws UnauthorizedException, BadRequestException {
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }

        try {
            return gameDAO.getGame(gameID);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    public AuthData getAuth(String authToken) throws UnauthorizedException {
        try {
            return authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
    }

    public AuthData authTokenGenerator(UserData userData) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(userData.username(), authToken);
        authDAO.addAuth(authData);
        return authData;
    }
}

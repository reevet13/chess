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

    public int createGame(String authToken, String gameName) throws UnauthorizedException, BadRequestException{
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e){
            throw new UnauthorizedException();
        }
        int gameID;
        do {
            gameID = ThreadLocalRandom.current().nextInt(1,10000);
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

    public boolean joinGame(String authToken, int gameID, String color) throws BadRequestException, UnauthorizedException {
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

        String whiteUsername = gameData.whiteUsername();
        String blackUsername = gameData.blackUsername();

        if (Objects.equals(color, "WHITE")) {
            if(whiteUsername != null && !whiteUsername.equals(authData.username())) {
                return false;
            }
            else {
                whiteUsername = authData.username();
            }
        } else if (Objects.equals(color, "BLACK")) {
            if(blackUsername != null && !blackUsername.equals(authData.username())) {
                return false;
            }
            else {
                blackUsername = authData.username();
            }
        } else {
            throw new BadRequestException("%s is not black or white".formatted(color));
        }
        try {
            gameDAO.updateGame(new GameData(gameID, whiteUsername, blackUsername, gameData.gameName(), gameData.game()));
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

    public void clear() {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    public AuthData authTokenGenerator(UserData userData) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(userData.username(), authToken);
        authDAO.addAuth(authData);
        return authData;
    }
}

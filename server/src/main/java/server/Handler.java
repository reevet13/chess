package server;

import com.google.gson.Gson;
import dataaccess.UnauthorizedException;
import model.*;
import service.Service;
import dataaccess.BadRequestException;
import spark.*;

public class Handler {

    Service service;

    public Handler(Service service){
        this.service = service;
    }

    public Object register(Request req, Response res) throws BadRequestException{
        UserData userData = new Gson().fromJson(req.body(), UserData.class);

        if (userData.username() == null || userData.password() == null) {
            throw new BadRequestException("No username and/or password given.");
        }

        try {
            AuthData authData = service.createUser(userData);
            res.status(200);
            return new Gson().toJson(authData);
        } catch (Exception bad) {
            res.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        }
    }

    public Object login(Request req, Response res) throws UnauthorizedException {
        UserData userData = new Gson().fromJson(req.body(), UserData.class);
        AuthData authData = service.loginUser(userData);
        res.status(200);
        return new Gson().toJson(authData);
    }

    public Object logout(Request req, Response res) throws UnauthorizedException {
        String authToken = req.headers("authorization");
        service.logoutUser(authToken);
        res.status(200);
        return "{}";
    }

    public Object createGame(Request req, Response res) throws BadRequestException, UnauthorizedException {
        if (!req.body().contains("\"gameName\":")) {
            throw new BadRequestException("Game name required");
        }
        GameData gameData = new Gson().fromJson(req.body(), GameData.class);
        String authToken = req.headers("authorization");
        int gameID = service.createGame(authToken, gameData.getGameName());
        res.status(200);
        return "{ \"gameID\": %d }".formatted(gameID);
    }

    public Object joinGame(Request req, Response res) throws BadRequestException, UnauthorizedException{
        if (!req.body().contains("\"gameID\":")){
            throw new BadRequestException("GameID required");
        }
        String authToken = req.headers("authorization");
        record JoinGameData(String playerColor, int gameID) {}
        JoinGameData joinData = new Gson().fromJson(req.body(), JoinGameData.class);
        boolean joined = service.joinGame(authToken, joinData.gameID(), joinData.playerColor());
        if (!joined) {
            res.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        }
        res.status(200);
        return "{}";
    }

    public Object listGames(Request req, Response res) throws UnauthorizedException{
        String authToken = req.headers("authorization");
        GamesList games = new GamesList(service.listGames(authToken));
        res.status(200);
        return new Gson().toJson(games);
    }



}

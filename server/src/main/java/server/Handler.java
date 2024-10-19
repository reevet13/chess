package server;

import com.google.gson.Gson;
import model.UserData;
import model.AuthData;
import service.Service;
import dataAccess.BadRequestException;
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
        } catch (BadRequestException bad){
            res.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        }
    }



}

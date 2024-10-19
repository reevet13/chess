package service;

import model.UserData;
import model.AuthData;
import dataAccess.*;

import java.util.UUID;

public class Service {

    UserDAO userDAO;
    AuthDAO authDAO;

    public Service(UserDAO userDAO, AuthDAO authDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData createUser(UserData userData) throws BadRequestException{

        try {
            userDAO.createUser(userData);
        } catch (DataAccessException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(userData.username(), authToken);
        authDAO.addAuth(authData);

        return authData;
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}

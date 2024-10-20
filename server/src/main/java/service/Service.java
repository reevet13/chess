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
            return authTokenGenerator(userData);
        } catch (DataAccessException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    public AuthData loginUser(UserData userData) throws UnauthorizedException {
        try {
            if (userDAO.authenticateUser(userData)) {
                return authTokenGenerator(userData); // Reusing the helper method
            } else {
                throw new UnauthorizedException();
            }
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }

    public AuthData authTokenGenerator(UserData userData) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(userData.username(), authToken);
        authDAO.addAuth(authData);
        return authData;
    }
}

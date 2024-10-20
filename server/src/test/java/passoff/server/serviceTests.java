package passoff.server;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.Service;

public class serviceTests {
    static Service service;
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;

    static UserData testUser;

    @BeforeAll
    static void init() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        service = new Service(userDAO, authDAO, gameDAO);
    }

    @BeforeEach
    void setup() {
        userDAO.clear();
        authDAO.clear();

        testUser = new UserData("username", "password", "email");
    }

    @Test
    @DisplayName("Valid Register")
    void createUserTestPass() throws BadRequestException, DataAccessException {
        AuthData auth = service.createUser(testUser);
        Assertions.assertEquals(authDAO.getAuth(auth.authToken()), auth);
    }

    @Test
    @DisplayName("Invalid Register")
    void createUserTestFail() throws BadRequestException {
        service.createUser(testUser);
        Assertions.assertThrows(BadRequestException.class, () -> service.createUser(testUser));
    }

    @Test
    @DisplayName("Proper Login User")
    void loginUserTestPass() throws BadRequestException, UnauthorizedException, DataAccessException {
        service.createUser(testUser);
        AuthData authData = service.loginUser(testUser);
        Assertions.assertEquals(authDAO.getAuth(authData.authToken()), authData);
    }

    @Test
    @DisplayName("Improper Login User")
    void loginUserTestFail() throws BadRequestException {
        Assertions.assertThrows(UnauthorizedException.class, () -> service.loginUser(testUser));
        service.createUser(testUser);
        UserData badPass = new UserData(testUser.username(), "wrong", testUser.email());
        Assertions.assertThrows(UnauthorizedException.class, () -> service.loginUser(badPass));
    }
}

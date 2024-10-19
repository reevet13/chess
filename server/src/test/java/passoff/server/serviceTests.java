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

    static UserData testUser;

    @BeforeAll
    static void init() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        service = new Service(userDAO, authDAO);
    }

    @BeforeEach
    void setup() {
        userDAO.clear();
        authDAO.clear();

        testUser = new UserData("Username", "password", "email");
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
}

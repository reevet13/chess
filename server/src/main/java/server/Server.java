package server;

import service.Service;
import dataAccess.*;
import spark.*;

public class Server {

    UserDAO userDAO;
    AuthDAO authDAO;

    static Service service;
    Handler handler;

    public Server() {

        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        //gameDAO = new SQLGameDAO();

        service = new Service(userDAO, authDAO);

        handler = new Handler(service);
    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.delete("/db", this::clear);
        Spark.post("/user", handler::register);
        Spark.post("/session", handler::login);

        Spark.exception(BadRequestException.class, this::badRequestExceptionHandler);
        Spark.exception(UnauthorizedException.class, this::unauthorizedExceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void clearDB(){
        service.clear();
    }

    private Object clear(Request req, Response res){
        clearDB();
        res.status(200);
        return "{}";
    }

    private void badRequestExceptionHandler(BadRequestException ex, Request req, Response res) {
        res.status(400);
        res.body("{ \"message\": \"Error: bad request\" }");
    }

    private void unauthorizedExceptionHandler(UnauthorizedException ex, Request req, Response res){
        res.status(401);
        res.body("{ \"message\": \"Error: unauthorized\" }");
    }
}

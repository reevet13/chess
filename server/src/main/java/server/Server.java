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
        //gameService = new GameService(gameDAO, authDAO);

        handler = new Handler(service);
        //gameHandler = new GameHandler(gameService);
    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.delete("/db", this::clear);
        Spark.post("/user", handler::register);

        Spark.init();

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
}

package ui;

import client.ServerFacade;
import model.GameData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class PostloginREPL {

    ServerFacade server;
    List<GameData> games;

    boolean inGame;

    public PostloginREPL(ServerFacade server) {
        this.server = server;
        games = new ArrayList<GameData>();
    }

    public void run() {
        boolean loggedIn = true;
        inGame = false;
        out.print(RESET_TEXT_COLOR + RESET_BG_COLOR);
        while (loggedIn && !inGame) {
            String[] input = getUserInput();
            switch (input[0]) {
                case "quit":
                    return;
                case "help":
                    printHelpMenu();
                    break;
                case "logout":
                    server.logout();
                    loggedIn = false;
                    break;
                case "list":
                    refreshGames();
                    printGames();
                    break;
                case "create":
                    if (input.length != 2) {
                        out.println("Please provide a name");
                        printCreate();
                        break;
                    }
                    server.createGame(input[1]);
                    out.printf("Created game: %s%n", input[1]);
                    break;
                /*case "join":
                    handleJoin(input);
                    break;
                case "observe":
                    handleObserve(input);
                    break;*/
                default:
                    out.println("Command not recognized, please try again");
                    printHelpMenu();
                    break;
            }
        }
        if (!loggedIn) {
            PreloginREPL preloginREPL = new PreloginREPL(server);
            preloginREPL.run();
        }
    }

    private String[] getUserInput() {
        out.print("\n[LOGGED IN] >>> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().split(" ");
    }

    private void refreshGames() {
        games = new ArrayList<GameData>();
        HashSet<GameData> gameList = server.listGames();
        games.addAll(gameList);
    }

    private void printGames() {
        for (int i = 0; i < games.size(); i++) {
            GameData game = games.get(i);
            String whiteUser = game.whiteUsername() != null ? game.whiteUsername() : "open";
            String blackUser = game.blackUsername() != null ? game.blackUsername() : "open";
            out.printf("%d -- Game Name: %S | White User: %s | Black User: %s %n", i, game.gameName(), whiteUser, blackUser);
        }
    }

    private void printHelpMenu() {
        printCreate();
        out.println("list - list all games");
        printJoin();
        printObserve();
        out.println("logout - log out of current user");
        out.println("quit - stop playing");
        out.println("help - show this menu");
    }

    private void printCreate() {
        out.println("create <NAME> - create a new game");
    }

    private void printJoin() {
        out.println("join <ID> [WHITE|BLACK] - join a game as color");
    }

    private void printObserve() {
        out.println("observe <ID> - observe a game");
    }
}

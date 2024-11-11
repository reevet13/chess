package ui;

import chess.ChessBoard;
import chess.ChessGame;
import client.ServerFacade;
import model.GameData;
import ui.EscapeSequences.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class PostloginREPL {

    ServerFacade server;
    List<GameData> games;

    public PostloginREPL(ServerFacade server) {
        this.server = server;
        games = new ArrayList<>();
    }

    public void run() {
        boolean loggedIn = true;
        while (loggedIn) {
            String[] input = getUserInput();
            switch (input[0]) {
                case "quit":
                    return;
                case "help":
                    printHelpMenu();
                    break;
                case "logout":
                    loggedIn = false;
                    break;
                case "list":
                    out.println(server.listGames());
                    break;
                case "create":
                    if (input.length != 2) {
                        out.println("Please provide a name");
                        printCreate();
                        break;
                    }
                    int gameID = server.createGame(input[1]);
                    out.printf("Created game, ID: %d%n", gameID);
                    break;
                case "join":
                    if (input.length != 3) {
                        out.println("Please provide a game ID and color choice");
                        printJoin();
                        break;
                    }
                    if (server.joinGame(Integer.parseInt(input[1]), input[2])) {
                        out.println("You have joined the game");
                    } else {
                        out.println("Game does not exist or color taken");
                        printJoin();
                        break;
                    }
                case "observe":
                    if (input.length != 2) {
                        out.println("Please provide a game ID");
                        printObserve();
                        break;
                    }
                    GameData observeGame = games.get(Integer.parseInt(input[1]));
                    if (server.joinGame(observeGame.gameID(), null)) {
                        out.println("You have joined the game as an observer");
                        new Printer(observeGame.game().getBoard()).printBoard();
                        break;
                    } else {
                        out.println("Game does not exist");
                        printObserve();
                        break;
                    }
                default:
                    out.println("Command not recognized, please try again");
                    printHelpMenu();
                    break;
            }
        }

        PreloginREPL preloginREPL = new PreloginREPL(server);
        preloginREPL.run();
    }

    private String[] getUserInput() {
        out.print("\n[LOGGED IN] >>> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().split(" ");
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
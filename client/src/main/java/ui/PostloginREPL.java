package ui;

import chess.ChessBoard;
import chess.ChessGame;
import client.ServerFacade;
import model.GameData;
import ui.EscapeSequences.*;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class PostloginREPL {

    ServerFacade server;
    List<GameData> games;

    public PostloginREPL(ServerFacade server) {
        this.server = server;
        games = new ArrayList<>();
    }

    public void run() {
        boolean loggedIn = true;
        out.print(RESET_TEXT_COLOR + RESET_BG_COLOR);
        while (loggedIn) {
            refreshGames();
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
                    int gameID = server.createGame(input[1]);
                    refreshGames();
                    out.printf("Created game, ID: %d%n", gameID);
                    break;
                case "join":
                    if (input.length != 3) {
                        out.println("Please provide a game ID and color choice");
                        printJoin();
                        break;
                    }
                    GameData joinGame = games.stream()
                            .filter(game -> game.gameID() == Integer.parseInt(input[1])) // Match by gameID
                            .findFirst()
                            .orElse(null);
                    if (joinGame != null) {
                        String color = input[2].toUpperCase();
                        if (color.equals("WHITE") && joinGame.whiteUsername() != null) {
                            out.println("The WHITE color is already taken.");
                        } else if (color.equals("BLACK") && joinGame.blackUsername() != null) {
                            out.println("The BLACK color is already taken.");
                        } else {
                            if (server.joinGame(joinGame.gameID(), color)) {
                                out.println("You have joined the game");
                                new Printer(joinGame.game().getBoard()).printBoard();
                                refreshGames();
                                break;
                            } else {
                                out.println("Color is already taken or game is full");
                            }
                        }

                    } else {
                        out.println("Game does not exist");
                        printJoin();
                        break;
                    }
                    break;
                case "observe":
                    if (input.length != 2) {
                        out.println("Please provide a game ID");
                        printObserve();
                        break;
                    }
                    GameData observeGame = games.stream()
                            .filter(game -> game.gameID() == Integer.parseInt(input[1])) // Find game by gameID
                            .findFirst()
                            .orElse(null);
                    if (observeGame == null) {
                        out.println("Game does not exist");
                        printJoin();
                        break;
                    }
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

    private void refreshGames() {
        games = new ArrayList<>();
        HashSet<GameData> gameList = server.listGames();
        games.addAll(gameList);
    }

    private void printGames() {
        for (int i = 1; i < games.size(); i++) {
            GameData game = games.get(i - 1);
            String whiteUser = game.whiteUsername() != null ? game.whiteUsername() : "open";
            String blackUser = game.blackUsername() != null ? game.blackUsername() : "open";
            out.printf("%d -- Game Name: %s  |  White: %s  |  Black: %s %n", i, game.gameName(), whiteUser, blackUser);
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
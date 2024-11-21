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
        boolean inGame = false;
        out.print(RESET_TEXT_COLOR + RESET_BG_COLOR);
        while (loggedIn && !inGame) {
            refreshGames();
            String[] input = getUserInput();
            String command = input[0];

            switch (command) {
                case "quit":
                    return;
                case "help":
                    printHelpMenu();
                    break;
                case "logout":
                    handleLogout();
                    loggedIn = false;
                    break;
                case "list":
                    listGames();
                    break;
                case "create":
                    handleCreateGame(input);
                    break;
                case "join":
                    inGame = handleJoinGame(input, inGame);
                    break;
                case "observe":
                    inGame = handleObserveGame(input, inGame);
                    break;
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

    private void handleLogout() {
        server.logout();
        out.println("Logged out successfully.");
    }

    private void handleCreateGame(String[] input) {
        if (input.length != 2) {
            out.println("Please provide a name");
            printCreate();
            return;
        }
        int gameID = server.createGame(input[1]);
        refreshGames();
        out.printf("Created game, ID: %d%n", gameID);
    }

    private boolean handleJoinGame(String[] input, boolean inGame) {
        if (input.length != 3 || !input[1].matches("\\d") ||
                !input[2].toUpperCase().matches("WHITE|BLACK")) {
            out.println("Please provide a game ID and color choice");
            printJoin();
            return false;
        }
        try {
            int gamePosition = Integer.parseInt(input[1]) - 1;
            if (games.isEmpty() || games.size() <= gamePosition) {
                refreshGames();
                if (games.isEmpty()) {
                    out.println("Error: Create a game");
                } else {
                    out.println("Error: No game by this number");
                    printGames();
                }
                return inGame;
            }
            GameData joinGame = games.get(gamePosition);
            return joinGame(input[2].toUpperCase(), joinGame, inGame);
        } catch (NumberFormatException e) {
            out.println("Invalid game position. Please enter a valid number.");
            return inGame;
        }
    }

    private boolean handleObserveGame(String[] input, boolean inGame) {
        if (input.length != 2 || !input[1].matches("\\d")) {
            out.println("Please provide a game ID");
            printObserve();
            return inGame;
        }
        try {
            int gamePosition = Integer.parseInt(input[1]) - 1;// Convert to zero-based index
            if (gamePosition < 0 || gamePosition >= games.size()) {
                refreshGames();
                if (games.isEmpty()){
                    out.println("Error: Create game");
                    return inGame;
                }
                if (games.size() <= gamePosition){
                    out.println("Invalid game position");
                    printGames();
                    return inGame;
                }
            }

            GameData observeGame = games.get(gamePosition);
            return observeGame(observeGame, inGame);
        } catch (NumberFormatException e) {
            out.println("Invalid game position. Please enter a valid number.");
            return inGame;
        }
    }


    private boolean joinGame(String color, GameData game, boolean inGame) {
        if ((color.equals("WHITE") && game.whiteUsername() != null) ||
            (color.equals("BLACK") && game.blackUsername() != null)) {
            out.println("The " + color + " color is already taken.");
            return inGame;
        }
        if (server.joinGame(game.gameID(), color)) {
            out.println("You have joined the game " + game.gameName());
            inGame = true;
            server.connectWS();
            server.joinPlayer(joinGame.gameID(), color);
            GameplayREPL gameplayREPL = new GameplayREPL(server, game, color);
            gameplayREPL.run();
            return inGame;
        } else {
            out.println("Color is already taken or game is full");
            return inGame;
        }
    }

    private boolean observeGame(GameData game, boolean inGame) {
        if (server.joinGame(game.gameID(), null)) {
            out.println("You have joined the game as an observer");
            inGame = true;
            server.connectWS();
            server.joinObserver(game.gameID());
            GameplayREPL gameplayREPL = new GameplayREPL(server, game, null);
            gameplayREPL.run();
            return inGame;
        } else {
            out.println("Game does not exist");
            printObserve();
            return inGame;
        }
    }

    private void listGames() {
        refreshGames();
        printGames();
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
        for (int i = 0; i < games.size(); i++) {
            GameData game = games.get(i);
            String whiteUser = game.whiteUsername() != null ? game.whiteUsername() : "open";
            String blackUser = game.blackUsername() != null ? game.blackUsername() : "open";
            out.printf("%d -- Game Name: %s  |  White: %s  |  Black: %s %n", i + 1, game.gameName(), whiteUser, blackUser);
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

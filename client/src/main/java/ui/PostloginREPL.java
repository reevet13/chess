package ui;

import chess.ChessGame;
import client.ServerFacade;
import model.GameData;

import java.util.*;

import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class PostloginREPL {

    ServerFacade server;
    List<GameData> games;

    boolean inGame;

    public PostloginREPL(ServerFacade server) {
        this.server = server;
        games = new ArrayList<>();
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
                    handleCreate(input);
                    break;
                case "join":
                    handleJoin(input);
                    break;
                case "observe":
                    handleObserve(input);
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
            String whiteUser = game.getWhiteUsername() != null ? game.getWhiteUsername() : "open";
            String blackUser = game.getBlackUsername() != null ? game.getBlackUsername() : "open";
            out.printf("%d -- Game Name: %s  |  White User: %s  |  Black User: %s %n", i,
                    game.getGameName(), whiteUser, blackUser);
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

    private void handleCreate(String[] input) {
        if (input.length != 2) {
            out.println("Please provide a name");
            printCreate();
            return;
        }
        server.createGame(input[1]);
        out.printf("Created game: %s%n", input[1]);
    }

    private void handleJoin(String[] input) {
        if (input.length != 3 || !input[1].matches("\\d+") ||
                !input[2].toUpperCase().matches("WHITE|BLACK")) {
            out.println("Please provide a valid game ID and color choice");
            printJoin();
            return;
        }

        int gameNum = Integer.parseInt(input[1]);
        if (validateGameID(gameNum)) {
            GameData joinGame = games.get(gameNum);
            ChessGame.TeamColor color = ChessGame.TeamColor.valueOf(input[2].toUpperCase());

            // Check if the color is already taken
            if ((color == ChessGame.TeamColor.WHITE && joinGame.getWhiteUsername() != null) ||
                    (color == ChessGame.TeamColor.BLACK && joinGame.getBlackUsername() != null)) {
                out.printf("The color %s is already taken. Please choose a different color.%n", color);
                return;
            }

            GameplayREPL gameplayREPL = new GameplayREPL(server, joinGame, color);
            server.connectWS(gameplayREPL);
            server.connect(joinGame.getGameID(), false, color); // Connect as a player

            out.printf("You have joined game '%s' as %s%n", joinGame.getGameName(), color);
            inGame = true;

            gameplayREPL.run();
        }
    }

    private void handleObserve(String[] input) {
        if (input.length != 2 || !input[1].matches("\\d+")) {
            out.println("Please provide a valid game ID");
            printObserve();
            return;
        }

        int gameNum = Integer.parseInt(input[1]);
        if (validateGameID(gameNum)) {
            GameData observeGame = games.get(gameNum);

            GameplayREPL gameplayREPL = new GameplayREPL(server, observeGame, null);
            server.connectWS(gameplayREPL);
            server.connect(observeGame.getGameID(), true, null); // Connect as an observer

            out.printf("You are now observing game '%s'%n", observeGame.getGameName());
            inGame = true;

            gameplayREPL.run();
        }
    }

    private boolean validateGameID(int gameNum) {
        if (games.isEmpty() || games.size() <= gameNum) {
            refreshGames();
            if (games.isEmpty()) {
                out.println("Error: No games available. Please create a game first.");
                return false;
            }
            if (games.size() <= gameNum) {
                out.println("Error: Invalid Game ID. Please select from the available games.");
                printGames();
                return false;
            }
        }
        return true;
    }

    private void printCreate() {
        out.println("create <NAME> - create a new game");
    }

    private void printJoin() {
        out.println("join <ID> [WHITE|BLACK] - join a game as a player");
    }

    private void printObserve() {
        out.println("observe <ID> - observe a game");
    }
}

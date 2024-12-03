package server;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.UnauthorizedException;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.messages.Error;
import websocket.messages.LoadGame;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;
import websocket.commands.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebsocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connection established: " + session.getRemoteAddress().getAddress());
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("WebSocket connection closed: " + session.getRemoteAddress().getAddress() +
                " - Reason: " + reason);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.printf("Received: %s\n", message);

        if (message.contains("\"commandType\":\"CONNECT\"")) {
            Connect command = new Gson().fromJson(message, Connect.class);
            Server.gameSessions.put(session, command.getGameID());

            if (command.isObserver()) {
                handleJoinObserver(session, command);
            } else {
                handleJoinPlayer(session, command);
            }
        }
        else if (message.contains("\"commandType\":\"MAKE_MOVE\"")) {
            MakeMove command = new Gson().fromJson(message, MakeMove.class);
            handleMakeMove(session, command);
        }
        else if (message.contains("\"commandType\":\"LEAVE\"")) {
            Leave command = new Gson().fromJson(message, Leave.class);
            handleLeave(session, command);
        }
        else if (message.contains("\"commandType\":\"RESIGN\"")) {
            Resign command = new Gson().fromJson(message, Resign.class);
            handleResign(session, command);
        }
    }
    private void handleJoinPlayer(Session session, Connect command) throws IOException {
        try {
            AuthData auth = Server.service.getAuth(command.getAuthString());
            boolean joined = Server.service.joinGame(command.getAuthString(), command.getGameID(),
                    command.getColor().toString());

            if (!joined) {
                Error error = new Error("Error: Spot taken by someone else");
                sendError(session, error);
                return;
            }

            GameData game = Server.service.getGameData(command.getAuthString(), command.getGameID());
            ChessGame.TeamColor joiningColor = command.getColor();

            boolean correctColor;
            if (joiningColor == ChessGame.TeamColor.WHITE) {
                correctColor = Objects.equals(game.getWhiteUsername(), auth.username());
            } else {
                correctColor = Objects.equals(game.getBlackUsername(), auth.username());
            }

            if (!correctColor) {
                Error error = new Error("Error: attempting to join with wrong color");
                sendError(session, error);
                return;
            }

            Notification notif = new Notification("%s has joined the game as %s".formatted(auth.username(),
                    command.getColor().toString()));
            broadcastMessage(session, notif);


            LoadGame load = new LoadGame(game.getGame());
            sendMessage(session, load);
        } catch (UnauthorizedException | BadRequestException e) {
            sendError(session, new Error("Error: Not authorized or not a valid game"));
        }
    }

    private void handleJoinObserver(Session session, Connect command) throws IOException {
        try {
            AuthData auth = Server.service.getAuth(command.getAuthString());
            GameData game = Server.service.getGameData(command.getAuthString(), command.getGameID());

            Notification notif = new Notification("%s has joined the game as an observer".formatted(auth.username()));
            broadcastMessage(session, notif);

            LoadGame load = new LoadGame(game.getGame());
            sendMessage(session, load);
        } catch (UnauthorizedException | BadRequestException e) {
            sendError(session, new Error("Error: Not authorized or not a valid game"));
        }
    }

    private void handleMakeMove(Session session, MakeMove command) throws IOException {
        try {
            AuthData auth = Server.service.getAuth(command.getAuthString());
            GameData game = Server.service.getGameData(command.getAuthString(), command.getGameID());
            ChessGame.TeamColor userColor = getTeamColor(auth.username(), game);
            if (userColor == null) {
                sendError(session, new Error("Error: You are observing this game"));
                return;
            }

            if (game.getGame().getGameOver()) {
                sendError(session, new Error("Error: can not make a move, game is over"));
                return;
            }

            if (game.getGame().getTeamTurn().equals(userColor)) {
                game.getGame().makeMove(command.getMove());

                Notification notif;
                ChessGame.TeamColor opponentColor = userColor == ChessGame.TeamColor.WHITE ? ChessGame.TeamColor.BLACK :
                        ChessGame.TeamColor.WHITE;

                if (game.getGame().isInCheckmate(opponentColor)) {
                    notif = new Notification("Checkmate! %s wins!".formatted(auth.username()));
                    game.getGame().setGameOver(true);
                } else if (game.getGame().isInStalemate(opponentColor)) {
                    notif = new Notification("Stalemate caused by %s's move! It's a tie!".formatted(auth.username()));
                    game.getGame().setGameOver(true);
                } else if (game.getGame().isInCheck(opponentColor)) {
                    notif = new Notification("A move has been made by %s, %s is now in check!".formatted(auth.username()
                            , opponentColor.toString()));
                } else {
                    notif = new Notification("A move has been made by %s".formatted(auth.username()));
                }
                broadcastMessage(session, notif);

                Server.service.updateGame(auth.authToken(), game);

                LoadGame load = new LoadGame(game.getGame());
                broadcastMessage(session, load, true);
            } else {
                sendError(session, new Error("Error: it is not your turn"));
            }
        } catch (UnauthorizedException e) {
            sendError(session, new Error("Error: Not authorized"));
        } catch (BadRequestException e) {
            sendError(session, new Error("Error: invalid game"));
        } catch (InvalidMoveException e) {
            System.out.println("****** error: " + e.getMessage() + "  " + command.getMove().toString());
            sendError(session, new Error("Error: invalid move " +
                    "(you might need to specify a promotion piece)"));
        }
    }

    private void handleLeave(Session session, Leave command) throws IOException {
        try {
            AuthData auth = Server.service.getAuth(command.getAuthString());

            Notification notif = new Notification("%s has left the game".formatted(auth.username()));
            broadcastMessage(session, notif);

            session.close();
        } catch (UnauthorizedException e) {
            sendError(session, new Error("Error: Not authorized"));
        }
    }

    private void handleResign(Session session, Resign command) throws IOException {
        try {
            AuthData auth = Server.service.getAuth(command.getAuthString());
            GameData game = Server.service.getGameData(command.getAuthString(), command.getGameID());
            ChessGame.TeamColor userColor = getTeamColor(auth.username(), game);

            String opponentUsername = userColor == ChessGame.TeamColor.WHITE ? game.getBlackUsername() :
                    game.getWhiteUsername();

            if (userColor == null) {
                sendError(session, new Error("Error: You are observing this game"));
                return;
            }

            if (game.getGame().getGameOver()) {
                sendError(session, new Error("Error: The game is already over!"));
                return;
            }

            game.getGame().setGameOver(true);
            Server.service.updateGame(auth.authToken(), game);
            Notification notif = new Notification("%s has forfeited, %s wins!".formatted(auth.username(),
                    opponentUsername));
            broadcastMessage(session, notif, true);
        } catch (UnauthorizedException e) {
            sendError(session, new Error("Error: Not authorized"));
        } catch (BadRequestException e) {
            sendError(session, new Error("Error: invalid game"));
        }
    }

    // Send the notification to all clients on the current game except the currSession
    public void broadcastMessage(Session currSession, ServerMessage message) throws IOException {
        broadcastMessage(currSession, message, false);
    }

    // Send the notification to all clients on the current game
    public void broadcastMessage(Session currSession, ServerMessage message, boolean toSelf) throws IOException {
        System.out.printf("Broadcasting (toSelf: %s): %s%n", toSelf, new Gson().toJson(message));
        for (Session session : Server.gameSessions.keySet()) {
            boolean inAGame = Server.gameSessions.get(session) != 0;
            boolean sameGame = Server.gameSessions.get(session).equals(Server.gameSessions.get(currSession));
            boolean isSelf = session == currSession;
            if ((toSelf || !isSelf) && inAGame && sameGame) {
                sendMessage(session, message);
            }
        }
    }

    public void sendMessage(Session session, ServerMessage message) throws IOException {
        session.getRemote().sendString(new Gson().toJson(message));
    }

    private void sendError(Session session, Error error) throws IOException {
        System.out.printf("Error: %s%n", new Gson().toJson(error));
        session.getRemote().sendString(new Gson().toJson(error));
    }

    private ChessGame.TeamColor getTeamColor(String username, GameData game) {
        if (username.equals(game.getWhiteUsername())) {
            return ChessGame.TeamColor.WHITE;
        }
        else if (username.equals(game.getBlackUsername())) {
            return ChessGame.TeamColor.BLACK;
        }
        else return null;
    }

}
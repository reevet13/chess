package client;

import chess.ChessGame;
import com.google.gson.Gson;
import ui.Printer;
import ui.GameplayREPL;
import websocket.messages.Error;
import websocket.messages.LoadGame;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.EscapeSequences.ERASE_LINE;
import static ui.EscapeSequences.moveCursorToLocation;

public class WebsocketCommunicator extends Endpoint {

    private Session session;
    private GameplayREPL gameplayREPL;

    public WebsocketCommunicator(String serverDomain, GameplayREPL gameplayREPL) throws Exception {
        this.gameplayREPL = gameplayREPL;
        try {
            URI uri = new URI("ws://" + serverDomain + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            // Set message handler after the connection is established
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    handleMessage(message);
                }
            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            // Throw an exception with a detailed message for easier debugging
            throw new Exception("Failed to connect to WebSocket server: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        // Store the session object when connection is established
        this.session = session;
        System.out.println("WebSocket connection established: " + session.getId());
    }

    private void handleMessage(String message) {
        // Handle incoming messages and parse them based on their type
        if (message.contains("\"serverMessageType\":\"NOTIFICATION\"")) {
            Notification notif = new Gson().fromJson(message, Notification.class);
            gameplayREPL.printNotification(notif.getMessage());
        }
        else if (message.contains("\"serverMessageType\":\"ERROR\"")) {
            Error error = new Gson().fromJson(message, Error.class);
            gameplayREPL.printNotification(error.getMessage());
        }
        else if (message.contains("\"serverMessageType\":\"LOAD_GAME\"")) {
            LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
            gameplayREPL.printLoadedGame(loadGame.getGame());
        }
    }


    public void sendMessage(String message) {
        // Ensure session is open before sending a message
        if (this.session != null && this.session.isOpen()) {
            this.session.getAsyncRemote().sendText(message);
        } else {
            System.out.println("WebSocket session is not open. Cannot send message.");
        }
    }
}
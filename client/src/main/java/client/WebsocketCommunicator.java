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
import java.util.LinkedList;

import static ui.EscapeSequences.ERASE_LINE;
import static ui.EscapeSequences.moveCursorToLocation;

public class WebsocketCommunicator extends Endpoint{
    Session session;

    public WebsocketCommunicator(String serverDomain) throws Exception {
        try {
            URI uri = new URI("ws://" + serverDomain + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    handleMessage(message);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            throw new Exception();
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {}

    public void handleMessage(String message) {
        if (message.contains("\"serverMessageType\":\"NOTIFICATION\"")) {
            Notification notification = new Gson().fromJson(message, Notification.class);
            printNotification(notification.getMessage());
        }
        else if (message.contains("\"serverMessageType\":\"LOAD_GAME\"")) {
            LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
            printMoveMade(loadGame.getGame());
        }
    }

    private void printNotification(String message) {
        System.out.print(ERASE_LINE + '\r');
        System.out.printf("\n%s\n[IN-GAME] >>> ", message);
    }

    private void printMoveMade(ChessGame game) {
        System.out.print(ERASE_LINE + "\r\nA move has been made\n");
        GameplayREPL.boardPrinter.updateGame(game);
        GameplayREPL.boardPrinter.printBoard(GameplayREPL.color, null);
        System.out.print("[IN-GAME] >>> ");
    }

    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }
}

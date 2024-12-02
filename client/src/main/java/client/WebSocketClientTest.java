package client;

public class WebSocketClientTest {
    public static void main(String[] args) {
        try {
            WebsocketCommunicator communicator = new WebsocketCommunicator("localhost:8080"); // Use the correct server domain and port
            communicator.sendMessage("Hello, server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
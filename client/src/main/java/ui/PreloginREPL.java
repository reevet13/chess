package ui;

import client.ServerFacade;

import java.util.Scanner;

import static java.lang.System.out;

public class PreloginREPL {

    ServerFacade server;
    PostloginREPL postloginREPL;

    public PreloginREPL(ServerFacade server) {
        this.server = server;
        postloginREPL = new PostloginREPL(server);
    }

    public void run() {
        boolean loggedIn = false;
        out.println("Welcome to Chess! Enter 'help' to get started.");
        while (!loggedIn) {
            String[] input = getUserInput();
            switch (input[0]) {
                case "quit":
                    return;
                case "help":
                    printHelpMenu();
                    break;
                case "login":
                    if (input.length != 3) {
                        out.println("Please provide a username and password");
                        printLogin();
                        break;
                    }
                    if (server.login(input[1], input[2])) {
                        out.println("You are now logged in");
                        loggedIn = true;
                    } else {
                        out.println("Username or password incorrect, please try again");
                        printLogin();
                    }
                    break;
                case "register":
                    if (input.length != 4) {
                        out.println("Please provide a username, password, and email");
                        printRegister();
                        break;
                    }
                    if (server.register(input[1], input[2], input[3])) {
                        out.println("You are now registered and logged in");
                        loggedIn = true;
                    } else {
                        out.println("Username already in use, please choose a new one");
                        printRegister();
                    }
                    break;
                default:
                    out.println("Command not recognized, please try again");
                    printHelpMenu();
                    break;
            }
        }

        postloginREPL.run();

    }

    private String[] getUserInput() {
        out.print("\n[LOGGED OUT] >>> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().split(" ");
    }

    private void printHelpMenu() {
        printRegister();
        printLogin();
        out.println("quit - stop playing");
        out.println("help - show this menu");
    }

    private void printRegister() {
        out.println("register <USERNAME> <PASSWORD> <EMAIL> - create a new user");
    }

    private void printLogin() {
        out.println("login <USERNAME> <PASSWORD> - login to an existing user");
    }


}
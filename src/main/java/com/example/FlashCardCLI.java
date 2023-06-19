package com.example;

import java.sql.*;
import java.util.*;

public class FlashCardCLI implements AppInterface {
    private List<AppInterface> apps;
    private Connection connection;
    private Scanner scanner;
    private DeckDAO dao;
    private List<Object> decks;
    private String tablename;
    private int deck_ID;

    public FlashCardCLI(DAOInterface dao2, Connection connection, Scanner scanner, String tablename) throws SQLException {
        this.dao = (DeckDAO) dao2;
        this.connection = connection;
        this.scanner = scanner;
        this.tablename = tablename;
    }

    @Override
    public void launch() throws SQLException {
        decks = dao.getAllElements(tablename);
        if (decks.isEmpty()) {
            System.out.println("\nThere are no decks in the database.\n");
            return;
        }

        for (Object deck : decks) {
            System.out.println(deck.toString());
        }

        System.out.print("\nEnter the ID of the deck you want to choose: ");
        if (!scanner.hasNextInt()) {
            System.out.println(
                    "\nInvalid input! (Enter number from 1 to " + dao.getAllElements(tablename).size() + ").\n");
            scanner.next();
            return;
        } else {
            deck_ID = scanner.nextInt();
            if (deck_ID > dao.getAllElements(tablename).size() || deck_ID < 1) {
                System.out.println(
                        "\nInvalid input! (Enter number from 1 to " + dao.getAllElements(tablename).size() + ").\n");
                return;
            }
        }

        apps = new ArrayList<>();
        FlashCardDAO dao = new FlashCardDAO(connection, deck_ID);
        apps.add(new PrinterApp(dao, "flashcards", 1));
        apps.add(new FlashCardEditApp(scanner, "flashcards", dao));
        apps.add(new PlayApp(scanner, dao,"flashcards" ));

        while (true) {
            System.out.println("\n----------- " + ((Deck) decks.get(deck_ID - 1)).getName()
                    + " -----------");
            printMenu(apps);
            if (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input! (Enter number from 1 to " + (apps.size() + 1) + ").\n");
                scanner.nextLine();
            } else {
                int inp = scanner.nextInt();
                if (inp == apps.size() + 1)
                    break;
                else if (inp > apps.size() + 1 || inp < 1) {
                    System.out.println("\nInvalid input! (Enter number from 1 to " + (apps.size() + 1) + ").\n");
                    continue;
                }
                apps.get(inp - 1).launch();
            }
        }
    }

    @Override
    public String printDescription() {
        return "Choose Deck.";
    }

    public void printMenu(List<AppInterface> apps) {
        for (int count = 0; count < apps.size(); count++) {
            System.out.println((count + 1) + ". " + apps.get(count).printDescription());
        }
        System.out.println((apps.size() + 1) + ". Exit.\n");
        System.out.print("Please select one option: ");
    }
}

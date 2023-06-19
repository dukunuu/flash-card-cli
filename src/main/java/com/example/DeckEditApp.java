package com.example;

import java.sql.*;
import java.util.*;

public class DeckEditApp implements AppInterface {
    private DeckDAO dao;
    private List<Object> decks;
    private String tableName = "decks";
    private Scanner scanner;

    public DeckEditApp(Scanner scanner, String tableName, DAOInterface dao2) {
        this.scanner = scanner;
        this.tableName = tableName;
        this.dao = (DeckDAO) dao2;
    }

    @Override
    public void launch() throws SQLException {
        decks = dao.getAllElements(tableName);
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Add New Deck.");
            System.out.println("2. Remove Deck.");
            System.out.println("3. Back.");
            System.out.print("Please select an option: ");
            String input = scanner.next();
            switch (input) {
                case "1":
                    addNewDeck();
                    exit = true;
                    break;
                case "2":
                    removeDeck();
                    exit = true;
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalit input:");
                    break;
            }
        }
    }

    @Override
    public String printDescription() {
        return "Add/Remove Decks.";
    }

    public void addNewDeck() throws SQLException {
        scanner.nextLine();
        Deck newDeck = new Deck();
        newDeck.setDeckID(decks.size()+1);
        System.out.print("\nDeck Name: ");
        newDeck.setName(scanner.nextLine());
        if (dao.add(tableName, newDeck)) {
            System.out.println("\nDeck added successfully.\n");
        }
    }

    public void removeDeck() throws SQLException {
        for (Object deck : decks) {
            System.out.println(deck.toString());
        }
        if (decks.isEmpty()) {
            System.out.println("\nThere are no Decks in the database.\n");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter the ID of the Deck you want to remove: ");
        int id = scanner.nextInt();
        if (dao.remove(tableName, id)) {
            System.out.println("\nDeck removed successfully!\n");
        } else {
            System.out.println("\nFailed to remove Deck.\n");
        }
    }
}

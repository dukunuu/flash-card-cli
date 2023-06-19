package com.example;

import java.sql.*;
import java.util.*;

public class FlashCardEditApp implements AppInterface {

    private Scanner scanner;
    private String tablename;
    private List<Object> flashcards;
    private FlashCardDAO dao;

    public FlashCardEditApp(Scanner scanner, String tablename, FlashCardDAO dao) {
        this.scanner = scanner;
        this.tablename = tablename;
        this.dao = dao;
    }

    @Override
    public void launch() throws SQLException {
        flashcards = dao.getAllElements(tablename);
        System.out.println("1. Add New Flashcard.");
        System.out.println("2. Remove Flashcard.");
        System.out.println("3. Back.");
        System.out.print("Please select an option: ");
        boolean exit = true;
        while (exit) {
            String input = scanner.next();
            switch (input) {
                case "1":
                    addNewFlashcard();
                    exit = false;
                    break;
                case "2":
                    removeFlashcard();
                    exit = false;
                    break;
                case "3":
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        }
    }

    public void addNewFlashcard() throws SQLException {
        scanner.nextLine();
        FlashCard newFC = new FlashCard();
        newFC.setId(flashcards.size()+1);
        System.out.print("Question: ");
        newFC.setQuestion(scanner.nextLine());
        System.out.print("Answer: ");
        newFC.setAnswer(scanner.nextLine());
        if (dao.add(tablename, newFC)) {
            System.out.println("\nFlashCard added successfully;\n");
        }
    }

    public void removeFlashcard() throws SQLException {
        if (flashcards.isEmpty()) {
            System.out.println("\nThere are no flashcards in the database.");
            return;
        }
        for (Object c : flashcards) {
            System.out.println(c.toString());
        }
        scanner.nextLine();
        System.out.print("\nEnter the ID of the flashcard you want to remove: ");
        int id = scanner.nextInt();
        if (dao.remove(tablename, id)) {
            System.out.println("\nFlashcard removed successfully!\n");
        } else {
            System.out.println("\nFailed to remove flashcard.\n");
        }
    }

    @Override
    public String printDescription() {
        return "Add/Remove Flashcards.";
    }
}

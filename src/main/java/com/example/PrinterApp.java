package com.example;

import java.sql.*;
import java.util.*;

public class PrinterApp implements AppInterface {
    private DAOInterface dao;
    private String tablename;
    private int identifier; // 1 is Flashcard, 2 is Deck

    public PrinterApp(DAOInterface dao, String tablename, int identifier) {
        this.dao = dao;
        this.tablename = tablename;
        this.identifier = identifier;
    }

    @Override
    public void launch() throws SQLException {
        List<Object> objects = dao.getAllElements(tablename);
        if (!objects.isEmpty()) {
            for (Object e : objects) {
                System.out.println(e.toString());
            }
        } else {
            if (identifier == 1) {
                System.out.println("\nThere are no Flashcards in the database\n");
            } else {
                System.out.println("\nThere are no Decks in the database\n");
            }
        }
    }

    @Override
    public String printDescription() {
        if (identifier == 1) {
            return "View all Flashcards.";
        } else {
            return "View all decks.";
        }
    }
}

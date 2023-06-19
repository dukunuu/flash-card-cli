package com.example;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        List<AppInterface> apps = new ArrayList<>();
        apps.add(new ConnectionApp("flashcards"));
        apps.get(0).launch();
        DAOInterface dao;
        dao = new DeckDAO(((ConnectionApp) apps.get(0)).getConnection());
        apps.add(new PrinterApp(dao, "decks", 2));
        apps.add(new DeckEditApp(sc, "decks", dao));
        apps.add(new FlashCardCLI(dao, ((ConnectionApp) apps.get(0)).getConnection(), sc, "decks"));

        if (!((ConnectionApp) apps.get(0)).connectionSuccess())
            System.exit(0);
        System.out.println(apps.get(0).printDescription());
        while (true) {
            printMenu(apps);
            if (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! (Enter number from 1 to " + (apps.size()) + ").\n");
                sc.nextLine();
            } else {
                int inp = sc.nextInt();
                if (inp == apps.size())
                    break;
                else if (inp > apps.size() || inp < 1) {
                    System.out.println("\nInvalid input! (Enter number from 1 to " + (apps.size()) + ").\n");
                    continue;
                }
                apps.get(inp).launch();
            }
        }
        sc.close();
        ((ConnectionApp) apps.get(0)).disconnect();
    }

    public static void printMenu(List<AppInterface> apps) {
        for (int count = 1; count < apps.size(); count++) {
            System.out.println(count + ". " + apps.get(count).printDescription());
        }
        System.out.println((apps.size()) + ". Exit.\n");
        System.out.print("Please select one option: ");
    }
}

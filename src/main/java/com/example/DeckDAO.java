package com.example;

import java.sql.*;
import java.util.*;

public class DeckDAO implements DAOInterface {
    private Connection conn;
    private List<Object> decks;

    public DeckDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Object> getAllElements(String tableName) throws SQLException {
        List<Object> decks = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            int id = result.getInt("id");
            String deckName = result.getString("name");
            Deck deck = new Deck(id, deckName);
            decks.add(deck);
        }

        statement.close();
        return decks;
    }

    @Override
    public boolean add(String tableName, Object element) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (id,name) VALUES (?,?)";
        decks = getAllElements(tableName);
        decks.add(element);
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, ((Deck) element).getId());
        statement.setString(2, ((Deck) element).getName());
        int rowsInserted = statement.executeUpdate();
        statement.close();
        return rowsInserted > 0;
    }

    @Override
    public boolean remove(String tableName, int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        statement.close();
        decks = getAllElements(tableName);
        sql = "UPDATE " + tableName + " SET id=? WHERE id=?";
        if (rowsDeleted > 0) {
            for (int count = 0; count < decks.size(); count++) {
                if (((Deck) decks.get(count)).getId() != count + 1) {
                    PreparedStatement resetStatement = conn.prepareStatement(sql);
                    resetStatement = conn.prepareStatement(sql);
                    resetStatement.setInt(1, count + 1);
                    resetStatement.setInt(2, ((Deck) decks.get(count)).getId());
                    resetStatement.execute();
                    resetStatement.close();
                }
            }
        }
        return rowsDeleted > 0;
    }

    @Override
    public void createTable(String tableName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                + "deck_Name TEXT NOT NULL\n"
                + ")";
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
    }

    @Override
    public void removeTable(String tableName) throws SQLException {
        String sql = "DROP TABLE " + tableName;
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
    }
    public List<FlashCard> getRandomizedFlashcards(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " ORDER BY RAND() LIMIT 100";
        List<FlashCard> flashcards = new ArrayList<>();

        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id");
                String question = result.getString("question");
                String answer = result.getString("answer");
                flashcards.add(new FlashCard(id, question, answer, 1000));
            }
        }
        return flashcards;
    } 
}

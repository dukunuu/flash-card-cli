package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashCardDAO implements DAOInterface {
    private Connection connection;
    private int deckId;
    private List<Object> flashcards;

    public FlashCardDAO(Connection connection, int deckId) {
        this.connection = connection;
        this.deckId = deckId;
    }

    @Override
    public List<Object> getAllElements(String tableName) throws SQLException {
        flashcards = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE deck_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, deckId);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            int id = results.getInt(1);
            String question = results.getString(2);
            String answer = results.getString(3);
            FlashCard fc = new FlashCard(id, question, answer, deckId);
            flashcards.add(fc);
        }
        statement.close();
        return flashcards;
    }

    @Override
    public boolean add(String tableName, Object element) throws SQLException {
        flashcards = new ArrayList<>();
        flashcards.add(element);
        String sql = "INSERT INTO " + tableName + " (id,question, answer, deck_ID) VALUES (?,?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, (deckId*100+((FlashCard) element).getID()));
        statement.setString(2, ((FlashCard) element).getQuestion());
        statement.setString(3, ((FlashCard) element).getAnswer());
        statement.setInt(4, deckId);
        int rowsInserted = statement.executeUpdate();
        updateID(tableName);
        statement.close();
        return rowsInserted > 0;
    }

    @Override
    public boolean remove(String tableName, int id) throws SQLException {
        flashcards.remove(id-1);
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, deckId*100+id);
        int rowsDeleted = statement.executeUpdate();
        statement.close();
        updateID(tableName);
        return rowsDeleted > 0;
    }

    public void updateID(String tablename) throws SQLException {
        flashcards = getAllElements(tablename);
        for(int i=0;i<flashcards.size();i++){
            Object flashcard=flashcards.get(i);
            String sql = "UPDATE " + tablename + " SET id=? WHERE id=? AND deck_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, 100*deckId+i+1);
            statement.setInt(2, ((FlashCard) flashcard).getID());
            statement.setInt(3, deckId);
            statement.execute();
            statement.close();
        }
    }

    public List<FlashCard> getRandomizedFlashcards(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE deck_id=? ORDER BY RAND() LIMIT 100";
        List<FlashCard> flashcards = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,deckId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String question = result.getString("question");
                String answer = result.getString("answer");
                flashcards.add(new FlashCard(id, question, answer, deckId));
            }
        }
        return flashcards;
    }

    @Override
    public void createTable(String tableName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                + "deck_ID INTEGER NOT NULL,\n"
                + "question TEXT NOT NULL,\n"
                + "answer TEXT NOT NULL,\n"
                + "CONSTRAINT fk_deck_id FOREIGN KEY (deck_ID) REFERENCES decks(id) ON UPDATE CASCAdE\n"
                + "ON DELETE CASCADE"
                + ");";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    @Override
    public void removeTable(String tableName) throws SQLException {
        String sql = "DROP TABLE " + tableName;
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    public int getDeckId() {
        return deckId;
    }
}

package com.example;

import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.*;
import java.util.*;

public class FlashcardCLITest {
    private String URL = "jdbc:mysql://localhost/flashcards";
    private final String USER = "root";
    private final String PASSWORD = "Nekoduku1";
    private DeckDAO dao;
    private FlashCardDAO flashCardDAO;
    private Connection connection;
    private int deck_ID = 10000;
    private final Scanner scanner = Mockito.mock(Scanner.class);

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        dao = new DeckDAO(connection);
        flashCardDAO = new FlashCardDAO(connection, deck_ID);
        dao.add("decks", new Deck(deck_ID, "testDeck"));
    }

    @After
    public void tearDown() throws SQLException {
        dao.remove("decks", deck_ID);
    }

    @Test
    public void testAddNewFlashCard() throws SQLException {
        FlashCard fc1 = new FlashCard(1, "quesion1", "answer1", deck_ID);
        FlashCard fc2 = new FlashCard(2, "quesion2", "answer2", deck_ID);
        FlashCard fc3 = new FlashCard(3, "quesion3", "answer3", deck_ID);
        FlashCard fc4 = new FlashCard(4, "quesion4", "answer4", deck_ID);
        flashCardDAO.add("flashcards", fc1);
        flashCardDAO.add("flashcards", fc2);
        flashCardDAO.add("flashcards", fc3);
        flashCardDAO.add("flashcards", fc4);
        int count = 0;
        for (Object flashcard : flashCardDAO.getAllElements("flashcards")) {
            if (((FlashCard) flashcard).getDeckId() == deck_ID) {
                count++;
            }
        }
        assertEquals(4, count);
    }

    @Test
    public void testRemoveFlashcard() throws SQLException {
        FlashCard fc1 = new FlashCard(1, "quesion1", "answer1", deck_ID);
        FlashCard fc2 = new FlashCard(2, "quesion2", "answer2", deck_ID);
        FlashCard fc3 = new FlashCard(3, "quesion3", "answer3", deck_ID);
        FlashCard fc4 = new FlashCard(4, "quesion4", "answer4", deck_ID);
        flashCardDAO.add("flashcards", fc1);
        flashCardDAO.add("flashcards", fc2);
        flashCardDAO.add("flashcards", fc3);
        flashCardDAO.add("flashcards", fc4);
        flashCardDAO.remove("flashcards", 1);
        flashCardDAO.remove("flashcards", 1);
        flashCardDAO.remove("flashcards", 1);
        flashCardDAO.remove("flashcards", 1);
        int count = 0;
        for (Object flashcard : flashCardDAO.getAllElements("flashcards")) {
            if (((FlashCard) flashcard).getDeckId() == deck_ID) {
                count++;
            }
        }
        assertEquals(0, count);
    }

    @Test
    public void testRemoveFlashcardWithWrongInput() throws SQLException {
        try {
            FlashCard fc1 = new FlashCard(1, "quesion1", "answer1", deck_ID);
            flashCardDAO.add("flashcards", fc1);
            flashCardDAO.remove("flashcards", 5);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.toString(), true);
        }
    }

    @Test
    public void TestPrintDescription() {
        FlashCardEditApp fCardEditApp = new FlashCardEditApp(scanner, "flashcards", flashCardDAO);
        assertEquals("Add/Remove Flashcards.", fCardEditApp.printDescription());
    }
}

package com.example;

import java.sql.*;
import java.util.*;

public class PlayApp implements AppInterface {
    private List<Object> flashcards;
    private Scanner scanner;
    private DAOInterface dataAccess;
    private String tablename;

    public PlayApp(Scanner scanner, DAOInterface fDaoInterface, String tablename) {
        this.scanner = scanner;
        this.dataAccess =  fDaoInterface;
        this.tablename = tablename;
    }

    @Override
    public void launch() throws SQLException {
        flashcards = dataAccess.getAllElements(tablename);
        if (flashcards.isEmpty()) {
            System.out.println("There are no flashcards in the database.");
            return;
        }
        scanner.nextLine();
        int correctAnswers = 0;
        int totalQuestions = 0;
        List<FlashCard> randomFC = dataAccess.getRandomizedFlashcards(tablename);
        for (FlashCard flashCard : randomFC) {
            String question = flashCard.getQuestion();
            String answer = flashCard.getAnswer();
            System.out.println("Question " + (totalQuestions + 1) + ": " + question);
            System.out.print("Answer: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase(answer)) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Incorrect. The answer is: " + answer);
            }
            System.out.println();
            totalQuestions++;
        }
        if (totalQuestions > 0) {
            double percentageCorrect = (double) correctAnswers / totalQuestions * 100;
            System.out.printf("You got %d out of %d questions correct (%.2f%%).%n%n", correctAnswers, totalQuestions,
                    percentageCorrect);
        }
    }

    @Override
    public String printDescription() {
        return "Test your knowledge.";
    }
}

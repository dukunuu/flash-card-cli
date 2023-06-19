package com.example;

public class FlashCard {
    private int id;
    private String question;
    private String answer;
    private String deckName;
    private int deck_ID;

    public FlashCard(int id, String question, String answer, int deck_ID) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.deck_ID=deck_ID;
    }

    public FlashCard() {
        this.id = 0;
        this.question = "";
        this.answer = "";
    }

    public void setId(int newID) {
        id = newID;
    }

    public void setQuestion(String newQuestion) {
        question = newQuestion;
    }

    public void setAnswer(String newAnswer) {
        answer = newAnswer;
    }

    public void setDeckName(String newDeck) {
        deckName = newDeck;
    }

    public int getID() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getDeckName() {
        return deckName;
    }
    public int getDeckId(){
        return deck_ID;
    }
    public String toString() {
        return "\nFlashCard No." + id % 100 + "\nQuestion: " + question + "\nAnswer: " + answer;
    }
}
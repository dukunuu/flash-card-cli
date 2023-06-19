package com.example;

import java.util.*;

public class Deck {
    private List<FlashCard> flashcards = new ArrayList<>();
    private int deck_ID;
    private String deckName;

    public Deck() {
        this.deck_ID = 0;
        this.deckName = "";
    }

    public Deck(int deck_ID, String deckName) {
        this.deck_ID = deck_ID;
        this.deckName = deckName;
    }

    public String getName() {
        return deckName;
    }

    public int getId() {
        return deck_ID;
    }

    public List<FlashCard> getFC() {
        return flashcards;
    }

    public int getDeckSize() {
        return flashcards.size();
    }

    public void addNewFlashcard(FlashCard flashCard) {
        flashcards.add(flashCard);
    }

    public String toString() {
        return "\nDeck No." + deck_ID + " " + deckName;
    }

    public void setDeckID(int id) {
        deck_ID = id;
    }

    public void setName(String name) {
        deckName = name;
    }
}

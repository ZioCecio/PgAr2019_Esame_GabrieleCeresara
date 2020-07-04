package game;

import java.util.ArrayList;

/**
 * Classe per rappresentare le informazioni relative ad ogni giocatore
 */
public class Player {
    public static final int NUM_OF_INITIAL_CARDS = 7;

    private String name;
    private PlayerDeck currentDeck;

    public Player(String name) {
        this.name = name;
        this.currentDeck = new PlayerDeck();
    }

    /**
     * Aggiunge una nuova carta al mazzo del giocatore (invocato quando pesca)
     * 
     * @param card
     */
    public void addCard(Card card) {
        this.currentDeck.addCard(card);
    }

    /**
     * Aggiunge N carte al mazzo del giocatore
     * 
     * @param cards
     */
    public void addCards(ArrayList<Card> cards) {
        this.currentDeck.addCards(cards);
    }

    /**
     * Rimuove dal mazzo la carta specificata
     * 
     * @param card
     */
    public void removeCard(Card card) {
        this.currentDeck.removeCard(card);
    }

    public String getName() {
        return name;
    }

    public PlayerDeck getCurrentDeck() {
        return currentDeck;
    }

    @Override
    public String toString() {
        return String.format("%s possiede le seguenti carte:\n%s", this.name, this.currentDeck.toString());
    }
}
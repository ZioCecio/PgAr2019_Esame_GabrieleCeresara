package game;

import java.util.ArrayList;

/**
 * Classe per rappresentare le informazioni relative ad ogni giocatore
 */
public class Player {
    private String name;
    private ArrayList<Card> currentDeck;

    public Player(String name, ArrayList<Card> currentDeck) {
        this.name = name;
        this.currentDeck = currentDeck;
    }

    /**
     * Aggiunge una nuova carta al mazzo del giocatore (invocato quando pesca)
     * 
     * @param card
     */
    public void addCard(Card card) {
        this.currentDeck.add(card);
    }

    /**
     * Trova tutte le carte "giocabili" in base alla carta specificata, che sarà
     * quella in cima al mazzo degli scarti
     * 
     * @param card
     * @return la lista di carte giocabili
     */
    public ArrayList<Card> findRightCards(Card card) {
        ArrayList<Card> cards = new ArrayList<Card>();

        for (Card c : this.currentDeck) {
            if (c.getColor() == card.getColor() || c.getValue() == card.getValue()) {
                cards.add(c);
            }
        }

        return cards;
    }

    public String getName() {
        return name;
    }
}
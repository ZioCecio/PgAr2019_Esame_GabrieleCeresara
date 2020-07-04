package game;

import java.util.Collections;
import java.util.ArrayList;

/**
 * Classe per gestire il mazzo di gioco
 */
public class Deck {
    public static final int NUM_CARDS = 80;
    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();

        for (int i = 0; i < NUM_CARDS; i++) {
            this.deck.add(new Card(i % 10, Color.values()[i / 20]));
        }

        this.shuffle();
    }

    /**
     * Costruttore che crea un nuovo mazzo a partire da una lista di carte
     * pre-esistenti (usato quando il mazzo finisce ed è necessario riempirlo con il
     * mazzo degli scarti)
     * 
     * @param cards
     */
    public Deck(ArrayList<Card> cards) {
        this.deck = new ArrayList<Card>(cards);

        this.shuffle();
    }

    /**
     * Mescola il mazzo
     */
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    /**
     * Controlla se il mazzo è vuoto
     * 
     * @return
     */
    public boolean isEmpty() {
        return this.deck.size() == 0;
    }

    /**
     * Ritorna il numero di carte presenti nel mazzo
     * 
     * @return
     */
    public int remainingCards() {
        return this.deck.size();
    }

    /**
     * Operazione di "pescaggio"
     * 
     * @return la carta in cima al mazzo
     */
    public Card getFirstCard() {
        return this.deck.remove(0);
    }

    /**
     * Pesca le prime N carte
     * 
     * @param howMuch quante carte pescare
     * @return
     */
    public ArrayList<Card> getFirstCards(int howMuch) {
        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < howMuch; i++) {
            cards.add(this.deck.remove(0));
        }

        return cards;
    }

    /**
     * Rimuove dal mazzo la carta specificata
     */
    public void removeCard(Card card) {
        this.deck.remove(card);
    }

    /**
     * Aggiunge una nuova carta al mazzo
     * 
     * @param card
     */
    public void addCard(Card card) {
        this.deck.add(card);
    }

    /**
     * Aggiunge N carte al mazzo
     * 
     * @param cards
     */
    public void addCards(ArrayList<Card> cards) {
        this.deck.addAll(cards);
    }

    @Override
    public String toString() {
        String deck = "";

        for (Card card : this.deck) {
            deck += card.toString() + "\n";
        }

        return deck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
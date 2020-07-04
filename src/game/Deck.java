package game;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Classe per gestire il mazzo di gioco
 */
public class Deck {
    public static final int NUM_CARDS = 80;

    /**
     * Viene usata una LinkedList per rendere più efficienti le operazioni di
     * mescolamento
     */
    private LinkedList<Card> deck;

    public Deck() {
        this.deck = new LinkedList<Card>();

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
    public Deck(LinkedList<Card> cards) {
        this.deck = new LinkedList<Card>(cards);

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
     * Operazione di "pescaggio"
     * 
     * @return la carta in cima al mazzo
     */
    public Card getFirstCard() {
        return this.deck.removeFirst();
    }

    @Override
    public String toString() {
        String deck = "";

        for (Card card : this.deck) {
            deck += card.toString() + "\n";
        }

        return deck;
    }
}
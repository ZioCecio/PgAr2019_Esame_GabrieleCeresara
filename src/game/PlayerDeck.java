package game;

import java.util.ArrayList;

/**
 * PlayerDeck estende un normale mazzo per il semplice fatto che le operazioni
 * che vanno eseguite su di esso sono le stesse che vanno fatte su un normale
 * mazzo, anche se è necessario un metodo in più
 */
public class PlayerDeck extends Deck {
    public PlayerDeck() {
        super(new ArrayList<Card>());
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

        for (Card c : this.getDeck()) {
            if (c.isCompatible(card)) {
                cards.add(c);
            }
        }

        return cards;
    }
}
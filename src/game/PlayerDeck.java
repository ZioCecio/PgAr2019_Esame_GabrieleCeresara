package game;

import java.util.ArrayList;

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
            if (c.getColor() == card.getColor() || c.getValue() == card.getValue()) {
                cards.add(c);
            }
        }

        return cards;
    }
}
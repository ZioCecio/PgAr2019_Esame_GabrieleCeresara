package game;

import java.util.ArrayList;

import utils.Manager;

public class Match {
    private ArrayList<Player> players;
    private int currentPlayer;

    private Deck deck;
    private ArrayList<Card> discardedCards;

    public Match() {
        this.players = Manager.getPlayers();
        this.currentPlayer = 0;

        this.deck = new Deck();
        this.discardedCards = new ArrayList<Card>();

        for (Player player : this.players) {
            player.addCards(this.deck.getFirstCards(Player.NUM_OF_INITIAL_CARDS));
        }

        discardedCards.add(this.deck.getFirstCard());
    }

    public Match(ArrayList<Player> players, Deck deck) {
        this.players = players;
        this.currentPlayer = 0;

        this.deck = deck;
        this.discardedCards = new ArrayList<Card>();

        for (Player player : this.players) {
            player.addCards(this.deck.getFirstCards(Player.NUM_OF_INITIAL_CARDS));
        }

        discardedCards.add(this.deck.getFirstCard());
    }

    public void playTurn() {
        Player turnPlayer = this.players.get(this.currentPlayer);
        Card lastCard = this.discardedCards.get(this.discardedCards.size() - 1);

        Manager.writeTurnInfo(turnPlayer, lastCard);

        Card drawCard = null;

        ArrayList<Card> playableCards = turnPlayer.getCurrentDeck().findRightCards(lastCard);

        if (playableCards.size() == 0) {
            drawCard = this.deck.getFirstCard();
            turnPlayer.addCard(drawCard);
            Manager.writeDrawCard(drawCard);
        }

        if (drawCard != null && drawCard.isCompatible(lastCard)) {
            playableCards.add(drawCard);
        }

        if (playableCards.size() != 0) {
            Card choosedCard = Manager.chooseCard(playableCards);
            turnPlayer.removeCard(choosedCard);
            this.discardedCards.add(choosedCard);
        }

        this.currentPlayer++;
        if (this.currentPlayer >= this.players.size()) {
            this.currentPlayer = 0;
        }

        Manager.writeSpaces();
    }

    public boolean isFinished() {
        boolean finished = false;

        for (Player player : this.players) {
            if (player.getCurrentDeck().isEmpty()) {
                finished = true;
            }
        }

        return finished;
    }
}
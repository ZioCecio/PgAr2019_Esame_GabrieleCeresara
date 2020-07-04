package game;

import java.util.ArrayList;

import utils.Manager;

public class Match {
    private ArrayList<Player> players;
    private int currentPlayer;

    private int turnDirection;

    private Deck deck;
    private ArrayList<Card> discardedCards;

    public Match() {
        this.players = Manager.getPlayers();
        this.currentPlayer = 0;

        this.turnDirection = 1;

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
        if (this.deck.isEmpty()) {
            this.recreateDeck();
        }

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

            this.performCardAction(choosedCard);
        }

        this.currentPlayer = this.getNextPlayerIndex();

        Manager.writeSpaces();
    }

    private int getNextPlayerIndex() {
        int index = this.currentPlayer += this.turnDirection;

        if (index >= this.players.size()) {
            index = 0;
        }
        if (index < 0) {
            index = this.players.size() - 1;
        }

        return index;
    }

    private void performCardAction(Card card) {
        Player nextPlayer = this.players.get(this.getNextPlayerIndex());

        switch (card.getType()) {
            case PESCA_DUE:
                if (this.deck.remainingCards() < 2) {
                    recreateDeck();
                }

                nextPlayer.addCards(this.deck.getFirstCards(2));
                System.out.println(String.format("%s ha pescato 2 carte", nextPlayer.getName()));
                break;

            case PESCA_QUATTRO:
                if (this.deck.remainingCards() < 4) {
                    recreateDeck();
                }

                nextPlayer.addCards(this.deck.getFirstCards(4));
                System.out.println(String.format("%s ha pescato 4 carte", nextPlayer.getName()));

                card.setColor(Manager.chooseColor());

                break;

            case STOP:
                this.currentPlayer = this.getNextPlayerIndex();
                System.out.println(String.format("%s salta il turno", nextPlayer.getName()));
                break;

            case CAMBIO_GIRO:
                System.out.println("Giro cambiato");
                this.turnDirection *= -1;
                break;

            case CAMBIO_COLORE:
                card.setColor(Manager.chooseColor());
                break;
        }
    }

    private void recreateDeck() {
        this.deck = new Deck(this.discardedCards);
        this.discardedCards.clear();
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
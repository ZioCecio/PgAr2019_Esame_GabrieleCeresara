package game;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import utils.Manager;
import utils.XMLManager;

public class Match {
    private ArrayList<Player> players;
    // "Punta" il giocatore che deve fare la sua mossa
    private int currentPlayer;

    // Indica in che direzione va il giro
    private int turnDirection;

    private Deck deck;
    private ArrayList<Card> discardedCards;

    private Date dateOfMatch;

    /**
     * Costruttore di una nuova partita.
     */
    public Match() {
        this.players = Manager.getPlayers();
        this.currentPlayer = 0;

        this.turnDirection = 1;

        this.deck = XMLManager.readDeck(Manager.selectedDeck(this.players));
        this.discardedCards = new ArrayList<Card>();

        this.dateOfMatch = new Date();

        for (Player player : this.players) {
            player.addCards(this.deck.getFirstCards(Player.NUM_OF_INITIAL_CARDS));
        }

        discardedCards.add(this.deck.getFirstCard());

        selectFirstPlayer();
    }

    /**
     * Permette di giocare un turno ad un giocatore (quello specificato dall'indice
     * currentPlayer)
     */
    public void playTurn() {
        // Se il mazzo è vuoto riutilizza quello degli scarti
        if (this.deck.isEmpty()) {
            this.recreateDeck();
        }

        // Legge a chi tocca e qual'è l'ultima carta giocata
        Player turnPlayer = this.players.get(this.currentPlayer);
        Card lastCard = this.discardedCards.get(this.discardedCards.size() - 1);

        Manager.writeTurnInfo(turnPlayer, lastCard);

        Card drawCard = null;

        ArrayList<Card> playableCards = turnPlayer.getCurrentDeck().findRightCards(lastCard);

        // Se il giocatore non ha nessuna carta giocabile, deve pescare
        if (playableCards.size() == 0) {
            drawCard = this.deck.getFirstCard();
            turnPlayer.addCard(drawCard);
            Manager.writeDrawCard(drawCard);
        }

        // Se la carta pescata è giocabile, la aggiunge alla lista di quelle giocabili
        if (drawCard != null && drawCard.isCompatible(lastCard)) {
            playableCards.add(drawCard);
        }

        // Mostra il menù di scelta della carta da giocare
        if (playableCards.size() != 0) {
            Card choosedCard = Manager.chooseCard(playableCards);
            turnPlayer.removeCard(choosedCard);
            this.discardedCards.add(choosedCard);

            this.performCardAction(choosedCard);
        }

        // "Punta" al giocatore successivo
        this.currentPlayer = this.getNextPlayerIndex();

        Manager.writeSpaces();
    }

    /**
     * Specifica qual'è l'indice del prossimo giocatore. Viene usato per non creare
     * ambiguità con i "CAMBIO GIRO"
     * 
     * @return l'indice del prossimo giocatore che deve giocare
     */
    private int getNextPlayerIndex() {
        int index = this.currentPlayer + this.turnDirection;

        if (index >= this.players.size()) {
            index = 0;
        }
        if (index < 0) {
            index = this.players.size() - 1;
        }

        return index;
    }

    /**
     * Ogni tipologia di carta ha un effetto diverso: in questo metodo vengono
     * gestiti tutti gli effetti di tutte le carte
     * 
     * @param card
     */
    private void performCardAction(Card card) {
        Player nextPlayer = this.players.get(this.getNextPlayerIndex());

        switch (card.getType()) {
            case PESCA_DUE:
                this.drawTwo(nextPlayer);
                break;

            case PESCA_QUATTRO:
                drawFour(nextPlayer, card);
                break;

            case STOP:
                this.stop(nextPlayer);
                break;

            case CAMBIO_GIRO:
                this.changeTurn();
                break;

            case CAMBIO_COLORE:
                this.changeColor(card);
                break;

            case CAMBIA_CARTE:
                this.changeDecks();
                break;
        }
    }

    /**
     * Fa pescare 2 carte al giocatore successivo
     * 
     * @param nextPlayer
     */
    private void drawTwo(Player nextPlayer) {
        if (this.deck.remainingCards() < 2) {
            recreateDeck();
        }

        nextPlayer.addCards(this.deck.getFirstCards(2));
        System.out.println(String.format("%s ha pescato 2 carte", nextPlayer.getName()));
    }

    /**
     * Fa pescare 4 carte al giocatore successivo e permette a giocatore attuale di
     * cambiare colore
     * 
     * @param nextPlayer
     * @param card
     */
    private void drawFour(Player nextPlayer, Card card) {
        if (this.deck.remainingCards() < 4) {
            recreateDeck();
        }

        nextPlayer.addCards(this.deck.getFirstCards(4));
        System.out.println(String.format("%s ha pescato 4 carte", nextPlayer.getName()));

        card.setColor(Manager.chooseColor());
    }

    /**
     * Fa saltare il turno al giocatore successivo
     * 
     * @param nextPlayer
     */
    private void stop(Player nextPlayer) {
        this.currentPlayer = this.getNextPlayerIndex();
        System.out.println(String.format("%s salta il turno", nextPlayer.getName()));
    }

    /**
     * Inverte la direzione del giro
     */
    private void changeTurn() {
        System.out.println("Giro cambiato");
        this.turnDirection *= -1;
    }

    /**
     * Cambia il colore da giocare
     * 
     * @param card
     */
    private void changeColor(Card card) {
        card.setColor(Manager.chooseColor());
    }

    /**
     * Esegue uno "shifting" (scambio) di carte tra tutti i giocatori
     */
    private void changeDecks() {
        if (this.turnDirection == 1) {
            PlayerDeck firstDeck = players.get(0).getCurrentDeck();

            for (int i = 0; i < players.size() - 1; i++) {
                players.get(i).setCurrentDeck(players.get(i + 1).getCurrentDeck());
            }

            players.get(players.size() - 1).setCurrentDeck(firstDeck);
        } else {
            PlayerDeck firstDeck = players.get(players.size() - 1).getCurrentDeck();

            for (int i = players.size() - 1; i > 0; i--) {
                players.get(i).setCurrentDeck(players.get(i - 1).getCurrentDeck());
            }

            players.get(0).setCurrentDeck(firstDeck);
        }
    }

    /**
     * Nel caso in cui il mazzo sia vuoto, lo riempie
     */
    private void recreateDeck() {
        this.deck = new Deck(this.discardedCards);
        this.discardedCards.clear();
    }

    /**
     * Controlla se la partita è finita
     * 
     * @return true se è finita, false altrimenti
     */
    public boolean isFinished() {
        boolean finished = false;

        for (Player player : this.players) {
            if (player.getCurrentDeck().isEmpty()) {
                finished = true;
            }
        }

        return finished;
    }

    /**
     * Simula il lancio dei dadi per decidere chi sia il primo a scartare
     */
    public void selectFirstPlayer() {
        Random random = new Random();
        int max = 7;

        System.out.println("Lancio di dadi per selezionare chi inizia: ");

        for (int i = 0; i < this.players.size(); i++) {
            int num = random.nextInt(6) + 1;

            System.out.println(String.format("%s: %d", this.players.get(i).getName(), num));

            if (num < max) {
                this.currentPlayer = i;
            }
        }

        System.out.println(String.format("Inizia %s\n\n", this.players.get(this.currentPlayer).getName()));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Date getDateOfMatch() {
        return dateOfMatch;
    }
}
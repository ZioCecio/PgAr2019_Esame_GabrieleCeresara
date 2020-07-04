package utils;

import java.util.ArrayList;

import game.Card;
import game.Color;
import game.Deck;
import game.Match;
import game.Player;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class Manager {
    private Match match;

    public Manager() {
        this.match = new Match(getPlayers(), new Deck());
    }

    /*
     * private ArrayList<Player> getPlayers() { ArrayList<Player> players = new
     * ArrayList<Player>();
     * 
     * int numOfPlayers = InputDati.leggiIntero("Inserire il numero di giocatori: ",
     * 2, 4);
     * 
     * for (int i = 0; i < numOfPlayers; i++) { players.add(new
     * Player(InputDati.leggiStringa(String.format("Nome del giocatore %d: ", i +
     * 1)))); }
     * 
     * return players; }
     */

    public static ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        int numOfPlayers = InputDati.leggiIntero("Inserire il numero di giocatori: ", 2, 4);

        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(InputDati.leggiStringa(String.format("Nome del giocatore %d: ", i + 1))));
        }

        return players;
    }

    public static Card chooseCard(ArrayList<Card> cards) {
        String[] fields = new String[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            fields[i] = cards.get(i).toString();
        }

        MyMenu menu = new MyMenu("Seleziona la carta da giocare: ", fields);

        int choose = menu.scegliSenzaUscita();

        return cards.get(choose - 1);
    }

    public static Color chooseColor() {
        MyMenu menu = new MyMenu("Seleziona il prossimo colore da giocare: ",
                new String[] { "BLU", "ROSSO", "VERDE", "GIALLO" });

        int choose = menu.scegliSenzaUscita();

        return Color.values()[choose - 1];
    }

    public static void writeTurnInfo(Player player, Card currentCard) {
        System.out.println(String.format("E' il turno di %s, con il seguente mazzo:\n%sL'ultima carta scartata è %s",
                player.getName(), player.getCurrentDeck(), currentCard));
    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void writeDrawCard(Card card) {
        System.out.println(String.format("Hai pescato la carta %s", card));
    }

    public static void writeSpaces() {
        System.out.println("\n\n\n\n\n\n");
    }
}
package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import game.Card;
import game.Color;
import game.Player;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class Manager {
    /**
     * Richiede all'utente quanti giocatori giocheranno e i loro nomi
     * 
     * @return la lista di giocatori
     */
    public static ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        int numOfPlayers = InputDati.leggiIntero("Inserire il numero di giocatori: ", 2, 4);

        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(InputDati.leggiStringa(String.format("Nome del giocatore %d: ", i + 1))));
        }

        return players;
    }

    /**
     * Mostra a schermo le carte giocabili e permette all'utente di selezionarne una
     * 
     * @param cards
     * @return la carta scelta dal giocatore
     */
    public static Card chooseCard(ArrayList<Card> cards) {
        String[] fields = new String[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            fields[i] = cards.get(i).toString();
        }

        MyMenu menu = new MyMenu("Seleziona la carta da giocare: ", fields);

        int choose = menu.scegliSenzaUscita();

        return cards.get(choose - 1);
    }

    /**
     * Permette di scegliere un colore tra quelli presenti nella lista (invocato
     * quando è necessario cambiare colore, ovvero quando un giocatore usa una carta
     * "CAMBIA COLORE" o "PESCA QUATTRO")
     * 
     * @return
     */
    public static Color chooseColor() {
        MyMenu menu = new MyMenu("Seleziona il prossimo colore da giocare: ",
                new String[] { "BLU", "ROSSO", "VERDE", "GIALLO" });

        int choose = menu.scegliSenzaUscita();

        return Color.values()[choose - 1];
    }

    /**
     * Scrive le principali informazioni del turno per permettere al giocatore di
     * capire lo stato della partita e quale possa essere la sua prossima mossa
     * 
     * @param player
     * @param currentCard
     */
    public static void writeTurnInfo(Player player, Card currentCard) {
        System.out
                .println(String.format("E' il turno di %s, con il seguente mazzo:\n%s\nL'ultima carta scartata è %s\n",
                        player.getName(), player.getCurrentDeck(), currentCard));
    }

    /**
     * Messaggio che comunica all'utente la carta che ha pescato
     * 
     * @param card
     */
    public static void writeDrawCard(Card card) {
        System.out.println(String.format("Hai pescato la carta %s", card));
    }

    /**
     * Scrive una riga di "=" per distanziare e rendere più leggibili le varie parti
     * di "interfaccia"
     */
    public static void writeSpaces() {
        System.out.println("========================================================================");
    }

    /**
     * Chiede ad ogni giocatore con quale mazzo vorrebbe giocare, per fare in modo
     * che si giochi con quello più votato
     * 
     * @param players
     * @return
     */
    public static String selectedDeck(ArrayList<Player> players) {
        File file = new File("./input");
        String[] fileNames = new String[file.listFiles().length];
        int[] votes = new int[fileNames.length];
        Arrays.fill(votes, 0);

        System.out.println(String.format("Sono stati trovati %d mazzi con cui giocare", fileNames.length));

        for (int i = 0; i < file.listFiles().length; i++) {
            fileNames[i] = file.listFiles()[i].getName();
        }

        for (Player player : players) {
            MyMenu menu = new MyMenu(String.format("%s seleziona il mazzo con cui vuoi giocare", player.getName()),
                    fileNames);

            int choose = menu.scegliSenzaUscita();
            votes[choose - 1]++;
        }

        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] > max) {
                max = votes[i];
                maxIndex = i;
            }
        }

        writeSpaces();
        System.out.println(String.format("Il mazzo selezionato è %s\n", fileNames[maxIndex]));

        return file.listFiles()[maxIndex].getPath();
    }
}
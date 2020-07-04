import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import game.Card;
import game.Color;
import game.Deck;
import game.Match;
import it.unibs.fp.mylib.MyMenu;
import utils.Manager;
import utils.PlayerModel;
import utils.XMLManager;

public class Main {
    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        MyMenu menu = new MyMenu("Benvenuto in UnoGiOh! Cosa vuoi fare?",
                new String[] { "Iniziare una nuova partita", "Visualizzare statistiche di una partita",
                        "Visualizzare la classifica dei giocatori con più vittorie" });

        int choose;

        do {
            choose = menu.scegli();

            switch (choose) {
                case 1:
                    playMatch();
                    break;

                case 2:
                    visualizeStats();
                    break;

                case 3:
                    visualizeRanking();
                    break;
            }
        } while (choose != 0);

    }

    private static void playMatch() {
        Match m = new Match();

        while (!m.isFinished()) {
            m.playTurn();
        }

        System.out.println(String.format("Il giocatore %s ha vinto", m.getWinner().getName()));

        XMLManager.writeStats(m);
        XMLManager.writeRanking(m);
    }

    private static void visualizeStats() {
        File file = new File("./stats");
        String[] fileNames = new String[file.listFiles().length - 1];

        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = file.listFiles()[i].getName();
        }

        MyMenu menu = new MyMenu("Quale partita vuoi visualizzare?", fileNames);

        int choose = menu.scegliSenzaUscita();

        XMLManager.showStats(file.listFiles()[choose - 1].getPath());
    }

    private static void visualizeRanking() {
        ArrayList<PlayerModel> playersInfo = XMLManager.getRanking();

        Collections.sort(playersInfo);

        for (int i = 0; i < playersInfo.size(); i++) {
            System.out.println(String.format("%d) %s => %d vittorie", i + 1, playersInfo.get(i).getName(),
                    playersInfo.get(i).getNumberOfVictory()));
        }
    }
}
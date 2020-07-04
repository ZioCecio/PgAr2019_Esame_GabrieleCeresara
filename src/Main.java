import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import game.Card;
import game.Color;
import game.Deck;
import game.Match;
import utils.Manager;
import utils.XMLManager;

public class Main {
    public static void main(String[] args) {

        Match m = new Match();

        while (!m.isFinished()) {
            m.playTurn();
        }

        XMLManager.writeStats(m);
    }
}
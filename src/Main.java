import game.Card;
import game.Color;
import game.Deck;
import game.Match;
import utils.Manager;

public class Main {
    public static void main(String[] args) {
        Match m = new Match();

        while (!m.isFinished()) {
            m.playTurn();
        }
    }
}
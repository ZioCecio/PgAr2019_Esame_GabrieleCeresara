package game;

import java.util.ArrayList;

public class Match {
    private ArrayList<Player> players;
    private Deck deck;

    public Match() {
        this.players = new ArrayList<Player>();
        this.deck = new Deck();
    }
}
package utils;

/**
 * Classe usata per rappresentare il formato in cui vengono salvate le
 * informazioni sul file XML
 */
public class PlayerModel {
    private String name;
    private int numberOfVictory;
    private int totalCards;
    private int totalMatches;

    public PlayerModel(String name, int numberOfVictory, int totalCards, int totalMatches) {
        this.name = name;
        this.numberOfVictory = numberOfVictory;
        this.totalCards = totalCards;
        this.totalMatches = totalMatches;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfVictory() {
        return numberOfVictory;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public boolean equals(PlayerModel pm) {
        return this.name.equals(pm.name);
    }

    public void increment(int victory, int cards) {
        this.numberOfVictory += victory;
        this.totalCards += totalCards;
        this.totalMatches++;
    }
}
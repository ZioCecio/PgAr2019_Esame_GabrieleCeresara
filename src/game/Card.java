package game;

/**
 * Classe che rappresenta una carta
 */
public class Card {
    private int value;
    private Color color;

    public Card(int value, Color color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public boolean isCompatible(Card card) {
        return (this.color == card.color || this.value == card.value);
    }

    @Override
    public String toString() {
        return String.format("%d %s", this.value, this.color.name());
    }
}
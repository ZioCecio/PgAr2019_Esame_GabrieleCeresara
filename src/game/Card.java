package game;

/**
 * Classe che rappresenta una carta
 */
public class Card {
    private int value;
    private Color color;
    private CardType type;

    public Card(int value, Color color, CardType type) {
        if (type == CardType.NUMERO) {
            this.value = value;
            this.color = color;
        } else if (type == CardType.PESCA_DUE || type == CardType.STOP || type == CardType.CAMBIO_GIRO) {
            // Anche le carte speciali hanno un loro valore
            this.value = -1 * (type.ordinal() + 2);
            this.color = color;
        } else {
            this.value = -1;
            this.color = null;
        }

        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Controlla se la carta specificata è compatibile con quella che richiama il
     * metodo (ovvero se è possibile giocare la carte specificata se l'ultima carta
     * scartata è questa)
     * 
     * @param card
     * @return
     */
    public boolean isCompatible(Card card) {
        if (this.type.isAlwaysCompatible()) {
            return true;
        }

        return (this.color == card.color || this.value == card.value);
    }

    @Override
    public String toString() {
        if (this.type == CardType.NUMERO) {
            return String.format("%d %s", this.value, this.color.name());
        }

        if (this.color == null) {
            return this.type.name();
        }

        return String.format("%s %s", this.type.name(), this.color.name());
    }
}
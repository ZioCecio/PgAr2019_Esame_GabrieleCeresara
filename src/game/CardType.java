package game;

public enum CardType {
    PESCA_DUE(false), STOP(false), CAMBIO_GIRO(false), PESCA_QUATTRO(true), CAMBIO_COLORE(true), CAMBIA_CARTE(true),
    NUMERO(false);

    private boolean alwaysCompatible;

    CardType(boolean alwaysCompatible) {
        this.alwaysCompatible = alwaysCompatible;
    }

    public boolean isAlwaysCompatible() {
        return this.alwaysCompatible;
    }
}
package model.Figures;

public enum FiguresTypes {
    /**
     * Король.
     */
    KING,

    /**
     * Ферзь.
     */
    QUEEN,

    /**
     * Пешка.
     */
    PAWN,

    /**
     * Конь.
     */
    KNIGHT,

    /**
     * Слон.
     */
    BISHOP,

    /**
     * Ладья.
     */
    ROOK,

    /**
     * Волшебник
     */
    WIZARD;

    @Override
    public String toString() {
        return switch (this) {
            case KING -> "Король";
            case QUEEN -> "Ферзь";
            case PAWN -> "Пешка";
            case KNIGHT -> "Конь";
            case BISHOP -> "Слон";
            case ROOK -> "Ладья";
            case WIZARD -> "Волшебник";
        };
    }
}

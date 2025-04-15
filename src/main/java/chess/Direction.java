package chess;

/**
 * Направления.
 */
public enum Direction {
    /**
     * Север.
     */
    NORTH,

    /**
     * Северо-восток.
     */
    NORTHEAST,

    /**
     * Восток.
     */
    EAST,

    /**
     * Юго-восток.
     */
    SOUTHEAST,

    /**
     * ЮГ.
     */
    SOUTH,

    /**
     * Юго-запад.
     */
    SOUTHWEST,

    /**
     * Запад.
     */
    WEST,

    /**
     * Северо-запад.
     */
    NORTHWEST;

    /**
     * Противоположное направление.
     */
    private Direction opposite;

    static {
        NORTH.opposite = SOUTH;
        NORTHEAST.opposite = SOUTHWEST;
        EAST.opposite = WEST;
        SOUTHEAST.opposite = NORTHWEST;
        SOUTH.opposite = NORTH;
        SOUTHWEST.opposite = NORTHEAST;
        WEST.opposite = EAST;
        NORTHWEST.opposite = SOUTHEAST;
    }

    /**
     * Получить противоположное направление.
     * @return противоположное направление
     */
    public Direction getOppositeDirection() { return opposite; }
}

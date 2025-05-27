package model;

/**
 * Направления.
 */
public enum Direction {
    /**
     * Север.
     */
    NORTH(-1,0),

    /**
     * Северо-восток.
     */
    NORTHEAST(-1,1),

    /**
     * Восток.
     */
    EAST(0,1),

    /**
     * Юго-восток.
     */
    SOUTHEAST(1,1),

    /**
     * ЮГ.
     */
    SOUTH(1,0),

    /**
     * Юго-запад.
     */
    SOUTHWEST(1,-1),

    /**
     * Запад.
     */
    WEST(0, -1),

    /**
     * Северо-запад.
     */
    NORTHWEST(-1,-1);

    /**
     * Смещение по строкам
     */
    private final int deltaRow;

    /**
     * Смещение по столбцам
     */
    private final int deltaCol;

    Direction(int deltaRow, int deltaCol){
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    /**
     * Получение смещения по строкам
     * @return смещение по строкам
     */
    public int getDeltaRow() {
        return deltaRow;
    }

    /**
     * Получение смещения по столбцам
     * @return смещение по столбцам
     */
    public int getDeltaCol() {
        return deltaCol;
    }

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

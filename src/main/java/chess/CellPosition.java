package chess;

/**
 * Позиция ячейки.
 */
public class CellPosition {

    /**
     * Координата X.
     */
    private final int x;

    /**
     * Координата Y.
     */
    private final int y;

    CellPosition(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    /**
     * Получить координату Х.
     * @return координата Х.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Получить координату Y.
     * @return координата Y.
     */
    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition cellPosition = (CellPosition) o;
        return x == cellPosition.x &&
                y == cellPosition.y;
    }
}

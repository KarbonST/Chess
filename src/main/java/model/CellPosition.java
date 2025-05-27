package model;

import java.util.Objects;

/**
 * Позиция ячейки.
 */
public class CellPosition {

    /**
     * Номер строки.
     */
    private final int row;

    /**
     * Номер столбца.
     */
    private final int col;

    public CellPosition(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Получить номер строки.
     * @return номер строки.
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Получить номер столбца.
     * @return номер столбца.
     */
    public int getCol(){
        return this.col;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition cellPosition = (CellPosition) o;
        return row == cellPosition.row &&
                col == cellPosition.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row,col);
    }
}

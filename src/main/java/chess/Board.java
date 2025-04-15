package chess;

import java.util.List;

/**
 * Доска.
 */
public class Board {

    /**
     * Список ячеек.
     */
    private List<Cell> cells;

    /**
     * Клон доски.
     */
    private Board clonedBoard;

    /**
     * Получить клон доски.
     * @return клон доски.
     */
    public Board getClone() {
        return this.clonedBoard; }

    /**
     * Получить список ячеек.
     * @return список ячеек
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Клонировать доску.
     */
    public void cloneDesk(){
        this.clonedBoard = this;
    }

}

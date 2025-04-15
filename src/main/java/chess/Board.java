package chess;

import java.util.List;

/**
 * Доска.
 */
public class Board {

    /**
     * Размер доски
     */
    private final static int BOARD_SIZE = 8;

    /**
     * Список ячеек.
     */
    private List<Cell> cells;

    /**
     * Клон доски.
     */
    private Board clonedBoard;

    Board(){
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                CellPosition cellPosition = new CellPosition(i,j);
                Cell cell = new Cell(cellPosition);
                this.addCell(cell);
            }
        }

        // Связываем соседние ячейки
        assignNeighbours();
    }

    /**
     * Связывание соседних ячеек друг с другом
     */
    private void assignNeighbours(){
        for (Cell cell: this.cells){

        }
    }

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
     * Добавить ячейку в список
     * @param cell ячейка
     */
    public void addCell(Cell cell){
        this.cells.add(cell);
    }


    /**
     * Клонировать доску.
     */
    public void cloneDesk(){
        this.clonedBoard = this;
    }

}

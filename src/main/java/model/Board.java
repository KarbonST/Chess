package model;

import java.util.HashMap;
import java.util.Map;

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
    private final Map<CellPosition, Cell> cells;


    public Board(){
        this.cells = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                CellPosition cellPosition = new CellPosition(i,j);
                Cell cell = new Cell(cellPosition);
                this.cells.putIfAbsent(cellPosition,cell);
            }
        }

        // Связываем ячейки
        assignNeighbours();
    }

    /**
     * Связывание ячеек доски
     */
    private void assignNeighbours(){

        // Перебираем все клетки доски
        for (Cell cell: this.cells.values()){

            // Перебираем всех возможных соседей клетки
            for(Direction direction: Direction.values()){
                int neighbourRow = cell.getPosition().getRow() + direction.getDeltaRow();
                int neighbourCol = cell.getPosition().getCol() + direction.getDeltaCol();

                // Входит ли соседняя ячейка в пределы доски
                if (isInsideTheBoard(neighbourRow, neighbourCol)){

                    // Получаем ячейку соседа
                    Cell neighbourCell = getCellByPosition(new CellPosition(neighbourRow, neighbourCol));

                    // Добавляем соседа для ячейки
                    if (neighbourCell != null){
                        cell.setNeighbour(neighbourCell, direction);
                    }
                }
            }
        }
    }

    /**
     * Получить список ячеек.
     * @return список ячеек
     */
    public Map<CellPosition, Cell> getCells() {
        return cells;
    }

    /**
     * Получить ячейку по позиции
     * @param cellPosition позиция искомой ячейки
     * @return искомая ячейка
     */
    public Cell getCellByPosition(CellPosition cellPosition){
        return this.cells.get(cellPosition);
    }

    /**
     * Получить размер доски
     * @return размер доски
     */
    public static int getBoardSize(){
        return BOARD_SIZE;
    }

    /**
     * Проверка на выход за пределы доски
     * @param row строка
     * @param col колонка
     * @return выходит ли позиция за пределы доски
     */
    public static boolean isInsideTheBoard(int row, int col){
        return (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE);
    }
}

package chess;

import chess.Figures.Figure;
import java.util.Map;

/**
 * Ячейка.
 */
public class Cell {
    /**
     * Позиция ячейки.
     */
    private final CellPosition position;

    /**
     * Соседние ячейки.
     */
    private Map<Direction, Cell> neighbours;

    /**
     * Фигура, находящаяся в ячейке.
     */
    private Figure figure;

    Cell(CellPosition cellPosition){
        this.position = cellPosition;
    }

    /**
     * Получить позицию ячейки.
     * @return позиция ячейки.
     */
    public CellPosition getPosition() {
        return position;
    }

    /**
     * Получить соседей ячейки.
     * @return соседи ячейки.
     */
    public Map<Direction, Cell> getNeighbours() {
        return neighbours;
    }

    /**
     * Получить фигуру, находящуюся в ячейке.
     * @return фигура, находящаяся в ячейке.
     */
    public Figure getFigure() {
        return figure;
    }

    /**
     * Добавить соседа.
     * @param neighbour сосед
     * @param direction направление
     */
    public void setNeighbour(Cell neighbour, Direction direction){
        this.neighbours.putIfAbsent(direction, neighbour);
    }
}

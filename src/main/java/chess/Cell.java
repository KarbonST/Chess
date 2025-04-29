package chess;

import chess.Figures.Figure;

import java.util.HashMap;
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
    private final Map<Direction, Cell> neighbours;

    /**
     * Фигура, находящаяся в ячейке.
     */
    private Figure figure;

    public Cell(CellPosition cellPosition){
        this.position = cellPosition;
        this.neighbours = new HashMap<>();
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
     * Получить соседа по направлению
     * @param direction направление
     */
    public Cell getNeighbour(Direction direction){
        return neighbours.get(direction);
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

    /**
     * Задать ячейке фигуру
     * @param figure фигура
     */
    public void setFigure(Figure figure){
        this.figure = figure;
        if (this.figure.getCell() == null){
            this.figure.setCell(this);
        }
    }

    /**
     * Удалить фигуру
     */
    public void unsetFigure(){
        Figure currentFigure = this.figure;
        this.figure = null;
        if (currentFigure.getCell() == this){
            currentFigure.unsetCell();
        }
    }
}

import Figures.Figure;

import java.util.List;

/**
 * Ячейка.
 */
public class Cell {
    private final CellPosition position;
    private List<Cell> neighbours;
    private Figure figure;

    // Конструктор
    Cell(CellPosition cellPosition){
        this.position = cellPosition;
    }

    // Геттеры
    public CellPosition getPosition() {
        return position;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }

    public Figure getFigure() {
        return figure;
    }

    // Сеттеры
    public void setNeighbour(Cell neighbour){
        this.neighbours.add(neighbour);
    }
}

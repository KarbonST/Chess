package chess.Trajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

import java.util.List;

/**
 * Траектория.
 */
public abstract class Trajectory {

    /**
     * Список позиций траектории
     */
    protected List<CellPosition> positions;

    /**
     * Доска
     */
    protected Board board;

    public Trajectory(Board board){
        this.board = board;
    }

    /**
     * Получить список позиций траектории
     */
    public List<CellPosition> getPositions(){
        return this.positions;
    }

    /**
     * Построить траекторию
     * @param startFigurePosition стартовая позиция фигуры
     * @param trajectoryDirection направление траектории
     */
    public abstract void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection);

    /**
     * Добавить ячейку в траекторию
     * @param cellPosition позиция ячейки
     */
    public void addCellInTrajectory(CellPosition cellPosition){
        this.positions.add(cellPosition);
    }
}

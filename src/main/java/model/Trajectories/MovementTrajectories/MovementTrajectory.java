package model.Trajectories.MovementTrajectories;
import model.Cell;
import model.Direction;
import model.Trajectories.Trajectory;

import java.util.ArrayList;
import java.util.List;

/**
 * Траектория движения.
 */
public class MovementTrajectory extends Trajectory{

    /**
     * @param directions направления
     * @param stepsCount количество возможных шагов
     * @param shiftPerStep смещение за один шаг по строкам и колоннам
     */
    public MovementTrajectory(List<Direction> directions, int stepsCount, int[] shiftPerStep){
        super(directions, stepsCount, shiftPerStep);

    }

    @Override
    public void buildTrajectory(Cell startCell) {
        super.buildTrajectory(startCell);

        List<Cell> cellsToRemove = new ArrayList<>();

        // Убрать все ячейки, в которых есть фигуры
        for(Cell cell: this.cells){
            if (cell.getFigure() != null){
                cellsToRemove.add(cell);
            }
        }
        deleteCells(cellsToRemove);
    }

}

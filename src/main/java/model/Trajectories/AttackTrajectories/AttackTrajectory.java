package model.Trajectories.AttackTrajectories;
import model.Cell;
import model.Direction;
import model.Trajectories.Trajectory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Траектория атаки.
 */
public class AttackTrajectory extends Trajectory{

    /**
     * Цвет команды
     */
    private final Color teamColor;

    /**
     * @param directions направления
     * @param stepsCount количество возможных шагов
     * @param shiftPerStep смещение за один шаг по строкам и колоннам
     * @param teamColor цвет команды
     */
    public AttackTrajectory(Color teamColor, List<Direction> directions, int stepsCount, int[] shiftPerStep){
        super(directions, stepsCount, shiftPerStep);
        this.teamColor = teamColor;
    }

    @Override
    public void buildTrajectory(Cell startCell) {
        super.buildTrajectory(startCell);

        List<Cell> cellsToRemove = new ArrayList<>();
        // Удалить все позиции ячеек с фигурами собственной команды и ячейки без фигур
        for (Cell cell: this.cells){
            if (cell.getFigure() == null || cell.getFigure().getTeam().getColor() == this.teamColor){
                cellsToRemove.add(cell);
            }
        }
        deleteCells(cellsToRemove);
    }
}

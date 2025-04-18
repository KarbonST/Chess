package chess.Trajectories.MovementTrajectories;
import chess.Board;
import chess.CellPosition;
import chess.Direction;
import chess.Trajectories.Trajectory;

import java.util.List;

/**
 * Траектория движения.
 */
public class MovementTrajectory extends Trajectory{

    MovementTrajectory(Board board, List<Direction> directions, int stepsCount, int[] shiftPerStep){
        super(board, directions, stepsCount, shiftPerStep);
    }

    @Override
    public void buildTrajectory(CellPosition startPosition) {
        super.buildTrajectory(startPosition);

        // Убрать все позиции, в которых есть фигуры
        for(CellPosition pos: this.positions){
            // Удалить ячейку с фигурой из траектории
            if (this.board.getCellByPosition(pos).getFigure() != null){
                deleteCell(pos);
            }
        }
    }
}

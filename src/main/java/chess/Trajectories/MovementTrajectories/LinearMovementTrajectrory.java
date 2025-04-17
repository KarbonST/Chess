package chess.Trajectories.MovementTrajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

/**
 * Линейная траектория движения.
 */
public class LinearMovementTrajectrory extends MovementTrajectory{

    LinearMovementTrajectrory(Board board){
        super(board);
    }

    @Override
    public void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection) {

    }
}

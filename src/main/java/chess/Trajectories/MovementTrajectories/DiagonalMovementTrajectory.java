package chess.Trajectories.MovementTrajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

/**
 * Диагональная траектория движения.
 */
public class DiagonalMovementTrajectory extends MovementTrajectory{

    DiagonalMovementTrajectory(Board board){
        super(board);
    }

    @Override
    public void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection) {

    }
}

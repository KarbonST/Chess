package chess.Trajectories.MovementTrajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

/**
 * Траектория движения буквой "Г".
 */
public class LShapeMovementTrajectory extends MovementTrajectory{

    LShapeMovementTrajectory(Board board){
        super(board);
    }

    @Override
    public void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection) {

    }
}

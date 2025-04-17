package chess.Trajectories.MovementTrajectories;
import chess.Board;
import chess.CellPosition;
import chess.Direction;
import chess.Trajectories.Trajectory;

/**
 * Траектория движения.
 */
public abstract class MovementTrajectory extends Trajectory{

    MovementTrajectory(Board board){
        super(board);
    }

}

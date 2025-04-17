package chess.Trajectories.AttackTrajectories;
import chess.Board;
import chess.CellPosition;
import chess.Direction;
import chess.Trajectories.Trajectory;

/**
 * Траектория атаки.
 */
public abstract class AttackTrajectory extends Trajectory{


    AttackTrajectory(Board board){
        super(board);
    }
}

package chess.Trajectories.AttackTrajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

/**
 * Линейная траектория атаки.
 */
public class LinearAttackTrajectory extends AttackTrajectory{

    LinearAttackTrajectory(Board board){
        super(board);
    }

    @Override
    public void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection) {

    }
}

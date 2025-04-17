package chess.Trajectories.AttackTrajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

/**
 * Диагональная траектория атаки.
 */
public class DiagonalAttackTrajectory extends AttackTrajectory{

    DiagonalAttackTrajectory(Board board){
        super(board);
    }

    @Override
    public void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection) {

    }
}

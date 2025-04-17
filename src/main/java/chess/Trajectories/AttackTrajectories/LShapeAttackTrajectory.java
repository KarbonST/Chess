package chess.Trajectories.AttackTrajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;

/**
 * Траектория атаки буквой "Г".
 */
public class LShapeAttackTrajectory extends AttackTrajectory{

    LShapeAttackTrajectory(Board board){
        super(board);
    }

    @Override
    public void buildTrajectory(CellPosition startFigurePosition, Direction trajectoryDirection) {

    }

}

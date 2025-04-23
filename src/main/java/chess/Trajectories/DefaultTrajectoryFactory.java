package chess.Trajectories;

import chess.Board;
import chess.Direction;
import chess.Trajectories.AttackTrajectories.AttackTrajectory;
import chess.Trajectories.MovementTrajectories.MovementTrajectory;

import java.awt.*;
import java.util.List;

public class DefaultTrajectoryFactory implements TrajectoryFactory{

    /**
     * Доска
     */
    private final Board board;

    public DefaultTrajectoryFactory(Board board){
        this.board = board;
    }

    @Override
    public AttackTrajectory createAttackTrajectory(List<Direction> directions, int stepsCount, int[] shiftPerStep, Color teamColor) {
        return new AttackTrajectory(this.board, teamColor, directions, stepsCount, shiftPerStep);
    }

    @Override
    public MovementTrajectory createMovementTrajectory(List<Direction> directions, int stepsCount, int[] shiftPerStep) {
        return new MovementTrajectory(this.board, directions, stepsCount, shiftPerStep);
    }


}

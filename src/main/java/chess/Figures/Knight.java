package chess.Figures;

import chess.Board;
import chess.Direction;
import chess.Team;
import chess.Trajectories.AttackTrajectories.AttackTrajectory;
import chess.Trajectories.MovementTrajectories.MovementTrajectory;
import chess.Trajectories.Trajectory;

import java.util.List;

/**
 * Конь.
 */
public class Knight extends Figure{

    Knight(Team team) {
        super(team);
        this.movementRadius = 1;
        this.attackRadius = 1;
        this.shiftPerStep = new int[][] { {1,2}, {2,1} };
        this.movementDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST);
        this.attackDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST);

        this.movementTrajectories = List.of(new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[0]),
                new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[1]));

        this.attackTrajectories = List.of(new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[0]),
                new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[1]));
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.KNIGHT;
    }

    @Override
    public Figure cloneFigure(){
        return new Knight(this.team);
    }
}

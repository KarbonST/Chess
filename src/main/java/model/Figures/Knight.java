package model.Figures;

import model.Direction;
import model.Team;
import model.Trajectories.AttackTrajectories.AttackTrajectory;
import model.Trajectories.MovementTrajectories.MovementTrajectory;
import java.util.List;

/**
 * Конь.
 */
public class Knight extends Figure{

    public Knight(Team team) {
        super(team);
        this.lives = 5;
        this.upgradeDamage = 2;
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
    public FiguresTypes getUpgradeFigureType() {
        return FiguresTypes.BishopKnight;
    }

    @Override
    public Figure cloneFigure(){
        return new Knight(this.team);
    }
}

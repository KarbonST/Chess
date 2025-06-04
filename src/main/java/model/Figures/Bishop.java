package model.Figures;

import model.Board;
import model.Direction;
import model.Team;
import model.Trajectories.AttackTrajectories.AttackTrajectory;
import model.Trajectories.MovementTrajectories.MovementTrajectory;
import java.util.List;

/**
 * Слон.
 */
public class Bishop extends Figure{

    public Bishop(Team team) {
        super(team);
        this.lives = 5;
        this.upgradeDamage = 1;
        this.movementRadius = Board.getBoardSize();
        this.attackRadius = Board.getBoardSize();
        this.shiftPerStep = new int[][] { {1,1} };
        this.movementDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST);
        this.attackDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST);

        this.movementTrajectories = List.of(new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[0]));
        this.attackTrajectories = List.of(new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[0]));
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.BISHOP;
    }

    @Override
    public FiguresTypes getUpgradeFigureType() {
        return null;
    }

    @Override
    public Figure cloneFigure(){
        return new Bishop(this.team);
    }
}

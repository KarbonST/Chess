package model.Figures;

import model.Board;
import model.Direction;
import model.Team;
import model.Trajectories.AttackTrajectories.AttackTrajectory;
import model.Trajectories.MovementTrajectories.MovementTrajectory;
import java.util.List;

/**
 * Ферзь.
 */
public class Queen extends Figure{

    public Queen(Team team) {
        super(team);
        this.lives = 8;
        this.upgradeDamage = 4;
        this.movementRadius = Board.getBoardSize();
        this.attackRadius = Board.getBoardSize();
        this.shiftPerStep = new int[][] { {1,1} };
        this.movementDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
        this.attackDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        this.movementTrajectories = List.of(new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[0]));
        this.attackTrajectories = List.of(new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[0]));
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.QUEEN;
    }

    @Override
    public FiguresTypes getUpgradeFigureType() {
        return null;
    }

    @Override
    public Figure cloneFigure(){
        return new Queen(this.team);
    }
}

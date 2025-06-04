package model.Figures;

import model.Board;
import model.Direction;
import model.Team;
import model.Trajectories.AttackTrajectories.AttackTrajectory;
import model.Trajectories.MovementTrajectories.MovementTrajectory;
import java.util.List;

/**
 * Ладья.
 */
public class Rook extends Figure{

    public Rook(Team team) {
        super(team);
        this.lives = 6;
        this.upgradeDamage = 3;
        this.movementRadius = Board.getBoardSize();
        this.attackRadius = Board.getBoardSize();
        this.shiftPerStep = new int[][] { {1,1} };
        this.movementDirections = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
        this.attackDirections = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        this.movementTrajectories = List.of(new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[0]));
        this.attackTrajectories = List.of(new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[0]));
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.ROOK;
    }

    @Override
    public FiguresTypes getUpgradeFigureType() {
        return null;
    }

    @Override
    public Figure cloneFigure(){
        return new Rook(this.team);
    }
}

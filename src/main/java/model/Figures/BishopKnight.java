package model.Figures;

import model.Board;
import model.Direction;
import model.Team;
import model.Trajectories.AttackTrajectories.AttackTrajectory;
import model.Trajectories.MovementTrajectories.MovementTrajectory;

import java.util.List;

public class BishopKnight extends Figure{
    protected BishopKnight(Team team) {
        super(team);

        this.movementRadius = Board.getBoardSize();
        this.attackRadius = Board.getBoardSize();
        this.shiftPerStep = new int[][] { {1,2}, {2,1} , {1,1}};
        this.movementDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST);
        this.attackDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST, Direction.SOUTHWEST);

        this.movementTrajectories = List.of(new MovementTrajectory(this.movementDirections, 1, this.shiftPerStep[0]),
                new MovementTrajectory(this.movementDirections, 1, this.shiftPerStep[1]),
                new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[2]));

        this.attackTrajectories = List.of(new AttackTrajectory(this.team.getColor(), this.attackDirections, 1, this.shiftPerStep[0]),
                new AttackTrajectory(this.team.getColor(), this.attackDirections, 1, this.shiftPerStep[1]),
                new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[2]));
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.BishopKnight;
    }

    @Override
    public FiguresTypes getUpgradeFigureType() {
        return null;
    }

    @Override
    public Figure cloneFigure() {
        return new BishopKnight(this.team);
    }
}

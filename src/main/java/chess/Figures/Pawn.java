package chess.Figures;

import chess.Cell;
import chess.Direction;
import chess.Team;
import chess.Trajectories.AttackTrajectories.AttackTrajectory;
import chess.Trajectories.MovementTrajectories.MovementTrajectory;
import chess.Trajectories.Trajectory;

import java.awt.*;
import java.util.List;

/**
 * Пешка.
 */
public class Pawn extends Figure{

    public Pawn(Team team) {
        super(team);
        this.movementRadius = 2;
        this.attackRadius = 1;
        this.shiftPerStep = new int[][] { {1,1} };

        if (this.team.getColor().equals(Color.WHITE)){
            this.movementDirections = List.of(Direction.NORTH);
            this.attackDirections = List.of(Direction.NORTHWEST, Direction.NORTHEAST);
        }
        else{
            this.movementDirections = List.of(Direction.SOUTH);
            this.attackDirections = List.of(Direction.SOUTHEAST, Direction.SOUTHWEST);
        }

        this.movementTrajectories = List.of(new MovementTrajectory(this.movementDirections, this.movementRadius, this.shiftPerStep[0]));
        this.attackTrajectories = List.of(new AttackTrajectory(this.team.getColor(), this.attackDirections, this.attackRadius, this.shiftPerStep[0]));
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.PAWN;
    }

    @Override
    public Figure cloneFigure(){
        return new Pawn(this.team);
    }

    @Override
    public UndoableMove moveTo(Cell targetCell){
        UndoableMove undoableMove = super.moveTo(targetCell);

        // Ход был совершен
        if (hasMoved()){
            setMovementRadius(1);
            for(Trajectory trajectory: getMovementTrajectories()){
                trajectory.setStepsCount(1);
            }
        }

        return undoableMove;
    }
}

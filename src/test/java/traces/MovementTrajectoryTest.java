package traces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Cell;
import chess.CellPosition;
import chess.Direction;
import chess.Figures.Figure;
import chess.Team;
import chess.Trajectories.MovementTrajectories.MovementTrajectory;
import chess.Figures.FiguresTypes;
import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovementTrajectoryTest {

    // Stub Figure: can't jump over
    static class DummyFigure extends Figure {
        DummyFigure(Team team) {
            super(team);
        }

        @Override
        public FiguresTypes getFigureType() {
            return FiguresTypes.PAWN;
        }

        @Override
        public Figure cloneFigure() {
            return new DummyFigure(team);
        }

        @Override
        public boolean canJumpOver() {
            return false;
        }
    }

    private Board board;
    private Team team;
    private Cell startCell;

    @BeforeEach
    void setUp() {
        board = new Board(); // 8x8, with neighbours assigned
        team = new Team(Color.WHITE);
        CellPosition startPos = new CellPosition(4, 4);
        startCell = board.getCellByPosition(startPos);
        // Place a DummyFigure on the start cell to provide canJumpOver()
        startCell.setFigure(new DummyFigure(team));
    }

    @Test
    void testCardinalMovesOneStep() {
        // Directions: N,S,E,W; 1 step; shiftPerStep {1,1}
        MovementTrajectory traj = new MovementTrajectory(
                List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST),
                1,
                new int[]{1, 1}
        );
        traj.buildTrajectory(startCell);
        List<Cell> cells = traj.getCells();

        assertEquals(4, cells.size());
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(3, 4))));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(5, 4))));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(4, 5))));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(4, 3))));
    }

    @Test
    void testBlockedByFigure() {
        // Place a blocking figure to the EAST
        Cell eastCell = board.getCellByPosition(new CellPosition(4, 5));
        eastCell.setFigure(new DummyFigure(team));

        MovementTrajectory traj = new MovementTrajectory(
                List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST),
                1,
                new int[]{1, 1}
        );
        traj.buildTrajectory(startCell);
        List<Cell> cells = traj.getCells();

        // East cell should be removed (blocked), others remain
        assertEquals(3, cells.size());
        assertFalse(cells.contains(eastCell));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(3, 4))));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(5, 4))));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(4, 3))));
    }

    @Test
    void testMultipleStepsNorth() {
        // Only NORTH; 2 steps
        MovementTrajectory traj = new MovementTrajectory(
                List.of(Direction.NORTH),
                2,
                new int[]{1, 1}
        );
        traj.buildTrajectory(startCell);
        List<Cell> cells = traj.getCells();

        assertEquals(2, cells.size());
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(3, 4))));
        assertTrue(cells.contains(board.getCellByPosition(new CellPosition(2, 4))));
    }

    @Test
    void testDoesNotGoOffBoard() {
        // Start at top edge
        Cell edgeCell = board.getCellByPosition(new CellPosition(0, 4));
        edgeCell.setFigure(new DummyFigure(team));

        MovementTrajectory traj = new MovementTrajectory(
                List.of(Direction.NORTH),
                3,
                new int[]{1, 1}
        );
        traj.buildTrajectory(edgeCell);
        List<Cell> cells = traj.getCells();

        assertTrue(cells.isEmpty(), "No moves off the board");
    }
}



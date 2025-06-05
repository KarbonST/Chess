package traces;

import model.Board;
import model.Cell;
import model.CellPosition;
import model.Direction;
import model.Figures.Figure;
import model.Figures.FiguresTypes;
import model.Team;
import model.Trajectories.AttackTrajectories.AttackTrajectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttackTrajectoryTest {

    // «Лёгкий» stub, чтобы контролировать canJumpOver() и цвет команды
    static class DummyFigure extends Figure {
        DummyFigure(Team team) {
            super(team);
        }

        @Override
        public FiguresTypes getFigureType() {
            return FiguresTypes.PAWN;
        }

        @Override
        public FiguresTypes getUpgradeFigureType() {
            return null;
        }

        @Override
        public Figure cloneFigure() {
            return new DummyFigure(getTeam());
        }

        @Override
        public boolean canJumpOver() {
            return false;
        }
    }

    private Board board;
    private Team whiteTeam;
    private Team blackTeam;
    private Cell startCell;

    @BeforeEach
    void setUp() {
        board = new Board();  // 8×8, соседние ячейки настроены
        whiteTeam = new Team(Color.WHITE);
        blackTeam = new Team(Color.BLACK);
        CellPosition pos = new CellPosition(4, 4);
        startCell = board.getCellByPosition(pos);
        // Ставим на стартовую клетку «свою» фигуру
        startCell.setFigure(new DummyFigure(whiteTeam));
    }

    @Test
    void testSingleStepEnemy() {
        // Своя к северу (будет удалена), враг к востоку (должен остаться)
        Cell north = board.getCellByPosition(new CellPosition(3, 4));
        north.setFigure(new DummyFigure(whiteTeam));
        Cell east = board.getCellByPosition(new CellPosition(4, 5));
        east.setFigure(new DummyFigure(blackTeam));

        AttackTrajectory traj = new AttackTrajectory(
                Color.WHITE,
                List.of(Direction.NORTH, Direction.EAST),
                1,
                new int[]{1,1}
        );
        traj.buildTrajectory(startCell);
        List<Cell> cells = traj.getCells();

        assertEquals(1, cells.size());
        assertTrue(cells.contains(east));
    }

    @Test
    void testBlockedByFriendPreventsEnemyBeyond() {
        // Шаг1: своя фигура, Шаг2: враг. Но своя блокирует, поэтому ничего не остаётся.
        Cell north1 = board.getCellByPosition(new CellPosition(3, 4));
        north1.setFigure(new DummyFigure(whiteTeam));
        Cell north2 = board.getCellByPosition(new CellPosition(2, 4));
        north2.setFigure(new DummyFigure(blackTeam));

        AttackTrajectory traj = new AttackTrajectory(
                Color.WHITE,
                List.of(Direction.NORTH),
                2,
                new int[]{1,1}
        );
        traj.buildTrajectory(startCell);
        assertTrue(traj.getCells().isEmpty());
    }

    @Test
    void testEnemyBeyondEmpty() {
        // Шаг1: пусто, Шаг2: враг => враг должен остаться
        Cell north2 = board.getCellByPosition(new CellPosition(2, 4));
        north2.setFigure(new DummyFigure(blackTeam));

        AttackTrajectory traj = new AttackTrajectory(
                Color.WHITE,
                List.of(Direction.NORTH),
                2,
                new int[]{1,1}
        );
        traj.buildTrajectory(startCell);
        List<Cell> cells = traj.getCells();

        assertEquals(1, cells.size());
        assertTrue(cells.contains(north2));
    }

    @Test
    void testNoEmptyOrOwn() {
        // Вообще пусто во всех направлениях => результат пуст
        AttackTrajectory traj = new AttackTrajectory(
                Color.WHITE,
                List.of(Direction.EAST),
                2,
                new int[]{1,1}
        );
        traj.buildTrajectory(startCell);
        assertTrue(traj.getCells().isEmpty());
    }
}


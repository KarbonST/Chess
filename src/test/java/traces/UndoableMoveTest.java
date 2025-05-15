package traces;

import chess.Board;
import chess.Cell;
import chess.CellPosition;
import chess.Figures.Pawn;
import chess.Figures.Rook;
import chess.Figures.UndoableMove;
import chess.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class UndoableMoveTest {

    private Board board;
    private Team whiteTeam;
    private Team blackTeam;

    @BeforeEach
    void setUp() {
        board = new Board();
        whiteTeam = new Team(Color.WHITE);
        blackTeam = new Team(Color.BLACK);
    }

    @Test
    void testSimpleMoveAndUndo() {
        // Расставляем ладью на (3,3)
        Cell start = board.getCellByPosition(new CellPosition(3, 3));
        Rook rook = new Rook(whiteTeam);
        start.setFigure(rook);

        // Целевая клетка на востоке (3,5)
        Cell target = board.getCellByPosition(new CellPosition(3, 5));
        // Строим траектории движения
        for (var traj : rook.getMovementTrajectories()) {
            traj.buildTrajectory(start);
        }

        // Выполняем ход
        UndoableMove move = rook.moveTo(target);

        // Проверяем состояние после хода
        assertSame(target, rook.getCell(),         "Rook should have moved to target");
        assertTrue(rook.hasMoved(),                "Rook’s hasMoved must be true");
        assertNull(start.getFigure(),              "Start cell must be empty after move");
        assertSame(rook, target.getFigure(),       "Target cell must hold the rook");

        // Отменяем ход
        move.undo();
        assertSame(start, rook.getCell(),          "Rook should return to start");
        assertFalse(rook.hasMoved(),               "Rook’s hasMoved must restore original state");
        assertNull(target.getFigure(),             "Target cell must be empty after undo");
        assertSame(rook, start.getFigure(),        "Start cell must hold the rook after undo");
    }

    @Test
    void testAttackMoveAndUndo() {
        // Расставляем ладью на (4,4)
        Cell start = board.getCellByPosition(new CellPosition(4, 4));
        Rook rook = new Rook(whiteTeam);
        start.setFigure(rook);

        // Ставим пешку врага на (4,6)
        Cell enemyCell = board.getCellByPosition(new CellPosition(4, 6));
        Pawn enemy = new Pawn(blackTeam);
        enemyCell.setFigure(enemy);

        // Строим траектории движения и атаки
        for (var traj : rook.getMovementTrajectories()) {
            traj.buildTrajectory(start);
        }
        for (var traj : rook.getAttackTrajectories()) {
            traj.buildTrajectory(start);
        }

        // Выполняем ход-атака
        UndoableMove move = rook.moveTo(enemyCell);

        // Проверяем состояние после атаки
        assertSame(rook, enemyCell.getFigure(),   "Rook should occupy enemy cell after attack");
        assertNull(start.getFigure(),              "Start cell must be empty after attack");
        assertFalse(blackTeam.getFigureList().contains(enemy),
                "Enemy must be removed from its team");

        // Отменяем ход-атака
        move.undo();
        assertSame(start, rook.getCell(),         "Rook should return to start after undo");
        assertSame(enemy, enemyCell.getFigure(),  "Enemy pawn must be restored after undo");
        assertTrue(blackTeam.getFigureList().contains(enemy),
                "Enemy must be re-added to its team");
    }

    @Test
    void testInvalidMoveDoesNothing() {
        // Расставляем ладью на (2,2)
        Cell start = board.getCellByPosition(new CellPosition(2, 2));
        Rook rook = new Rook(whiteTeam);
        start.setFigure(rook);

        // Целевая диагональная клетка (3,3) не в траектории ладьи
        Cell invalid = board.getCellByPosition(new CellPosition(3, 3));
        for (var traj : rook.getMovementTrajectories()) {
            traj.buildTrajectory(start);
        }

        // Выполняем «ход»
        UndoableMove move = rook.moveTo(invalid);

        // Ничего не должно измениться
        assertSame(start, rook.getCell(),        "Rook must remain at start for invalid move");
        assertFalse(rook.hasMoved(),              "hasMoved remains false for invalid move");
        assertNull(invalid.getFigure(),           "Invalid cell remains empty after invalid move");

        // Отмена тоже не приводит к изменениям
        move.undo();
        assertSame(start, rook.getCell(),        "Rook still at start after undo invalid");
        assertNull(invalid.getFigure(),           "Invalid cell still empty after undo invalid");
    }
}


package traces;

import chess.Board;
import chess.Cell;
import chess.CellPosition;
import chess.Figures.King;
import chess.Figures.Pawn;
import chess.Figures.Rook;
import chess.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

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
    void testAddAndDeleteFigure() {
        Pawn pawn = new Pawn(whiteTeam);
        assertTrue(whiteTeam.getFigureList().isEmpty(), "Initially empty");

        whiteTeam.addFigure(pawn);
        assertTrue(whiteTeam.getFigureList().contains(pawn), "Added pawn");
        assertEquals(whiteTeam, pawn.getTeam(), "Pawn’s team set");

        whiteTeam.deleteFigure(pawn);
        assertFalse(whiteTeam.getFigureList().contains(pawn), "Deleted pawn");
        assertNull(pawn.getTeam(), "Pawn’s team unset");
    }

    @Test
    void testGetKingCell() {
        Cell cell = board.getCellByPosition(new CellPosition(2, 2));
        King king = new King(blackTeam);
        cell.setFigure(king);
        blackTeam.addFigure(king);

        assertEquals(cell, blackTeam.getKingCell(),
                "Should return the king’s cell");
    }

    @Test
    void testCanAttackEnemyKing() {
        // Rook at (0,0)
        Cell rookCell = board.getCellByPosition(new CellPosition(0, 0));
        Rook rook = new Rook(whiteTeam);
        rookCell.setFigure(rook);
        whiteTeam.addFigure(rook);

        // Enemy king on same row
        Cell kingCell1 = board.getCellByPosition(new CellPosition(0, 7));
        King enemyKing1 = new King(blackTeam);
        kingCell1.setFigure(enemyKing1);
        blackTeam.addFigure(enemyKing1);

        whiteTeam.buildAttackTrajectories();
        assertTrue(whiteTeam.canAttackEnemyKing(kingCell1),
                "Rook should attack along row");

        // Enemy king on unreachable diagonal
        Cell kingCell2 = board.getCellByPosition(new CellPosition(1, 1));
        King enemyKing2 = new King(blackTeam);
        kingCell2.setFigure(enemyKing2);
        blackTeam.addFigure(enemyKing2);

        assertFalse(whiteTeam.canAttackEnemyKing(kingCell2),
                "Rook cannot attack diagonal");
    }

    @Test
    void testHasFigureWithAvailableMoves() {
        // Pawn in corner (0,0) — no moves
        Cell corner = board.getCellByPosition(new CellPosition(0, 0));
        Pawn pawn1 = new Pawn(whiteTeam);
        corner.setFigure(pawn1);
        whiteTeam.addFigure(pawn1);
        whiteTeam.buildMovementTrajectories();
        whiteTeam.buildAttackTrajectories();
        assertFalse(whiteTeam.hasFigureWithAvailableMoves(),
                "Pawn in corner has no moves");

        // Pawn in center — has moves
        Cell center = board.getCellByPosition(new CellPosition(3, 3));
        Pawn pawn2 = new Pawn(whiteTeam);
        center.setFigure(pawn2);
        whiteTeam.addFigure(pawn2);
        whiteTeam.buildMovementTrajectories();
        whiteTeam.buildAttackTrajectories();
        assertTrue(whiteTeam.hasFigureWithAvailableMoves(),
                "Pawn in center has moves");
    }

    @Test
    void testMoveFigureFrozen() {
        // Pawn at (4,4)
        Cell start = board.getCellByPosition(new CellPosition(4, 4));
        Pawn pawn = new Pawn(whiteTeam);
        start.setFigure(pawn);
        whiteTeam.addFigure(pawn);

        // Freeze — cannot move
        pawn.freeze();
        Cell target = board.getCellByPosition(new CellPosition(3, 4));
        whiteTeam.moveFigure(target, pawn);
        assertSame(start, pawn.getCell(),
                "Frozen pawn must not move");

        // Unfreeze — can move
        pawn.unfreeze();
        // Build its movement trajectory
        for (var traj : pawn.getMovementTrajectories()) {
            traj.buildTrajectory(start);
        }
        whiteTeam.moveFigure(target, pawn);
        assertSame(target, pawn.getCell(),
                "Unfrozen pawn must move");
    }
}


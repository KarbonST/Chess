package traces;

import chess.*;
import chess.Figures.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class JudgeTest {

    private Board board;
    private Team white, black;
    private Judge judge;

    @BeforeEach
    void setUp() {
        board = new Board();
        white = new Team(Color.WHITE);
        black = new Team(Color.BLACK);
        judge = new Judge(board);
    }

    /** Helper: add a figure to board and team */
    private void place(Figure fig, int row, int col) {
        Cell cell = board.getCellByPosition(new CellPosition(row, col));
        cell.setFigure(fig);
    }

    @Test
    void testGameIsOn() {
        // White king far from black king, no attacking pieces
        Figure k1 = new King(white);
        Figure k2 = new King(black);
        k1.setTeam(white);
        k2.setTeam(black);

        place(k1, 0, 0);
        place(k2, 7, 7);
        // build all trajectories
        white.buildMovementTrajectories();
        white.buildAttackTrajectories();
        black.buildMovementTrajectories();
        black.buildAttackTrajectories();

        assertEquals(GameStatus.GAME_IS_ON,
                judge.determineGameStatus(white, black));
    }

    @Test
    void testCheck() {
        Figure k1 = new King(white);
        Figure k2 = new King(black);
        Figure r1 = new Rook(white);
        k1.setTeam(white);
        k2.setTeam(black);
        r1.setTeam(white);

        // Black king at (7,7), white rook attacking along row
        place(k1, 0, 0);
        place(k2, 7, 7);
        place(r1, 7, 5);

        white.buildAttackTrajectories();
        white.buildMovementTrajectories();
        black.buildAttackTrajectories();
        black.buildMovementTrajectories();

        // Black has (6,7) or (7,6) free => only CHECK
        assertEquals(GameStatus.CHECK,
                judge.determineGameStatus(white, black));
    }

    @Test
    void testCheckmate() {
        Figure k1 = new King(white);
        Figure k2 = new King(black);
        Figure r1 = new Rook(white);
        Figure r2 = new Rook(white);
        k1.setTeam(white);
        k2.setTeam(black);
        r1.setTeam(white);
        r2.setTeam(white);

        // Black king flanked by two white rooks:
        // one at (6,6), one at (6,7): king at (7,7) no escape
        place(k1, 0, 0);
        place(k2, 7, 7);
        place(r1, 6, 6);
        place(r2, 6, 7);

        white.buildAttackTrajectories();
        white.buildMovementTrajectories();
        black.buildAttackTrajectories();
        black.buildMovementTrajectories();

        assertEquals(GameStatus.CHECKMATE,
                judge.determineGameStatus(white, black));
    }

    @Test
    void testStalemate() {
        // Black king at (7,7), no check but no legal moves:
        // white king at (5,6) and queen at (6,5) cover all adjacents
        place(new King(white), 5, 6);
        place(new Queen(white), 6, 5);
        place(new King(black), 7, 7);

        white.buildAttackTrajectories();
        white.buildMovementTrajectories();
        black.buildAttackTrajectories();
        black.buildMovementTrajectories();

        assertEquals(GameStatus.STALEMATE,
                judge.determineGameStatus(white, black));
    }
}


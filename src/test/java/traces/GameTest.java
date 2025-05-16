package traces;

import chess.Board;
import chess.Team;
import chess.Figures.King;
import chess.Figures.Rook;
import chess.Judge;
import chess.events.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.CellPosition;
import chess.Game;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private TestFigureListener figureListener;
    private TestGameStatusListener statusListener;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.start();
        figureListener = new TestFigureListener();
        statusListener = new TestGameStatusListener();
        game.addFigureActionListener(figureListener);
        game.addGameFinishActionListener(statusListener);
    }

    @Test
    void testOnFigureSelectedValid() {
        // White pawn at (6,0) is activeTeam's piece
        CellPosition pawnPos = new CellPosition(6, 0);
        game.onFigureSelected(pawnPos);
        assertEquals(1, figureListener.activatedCount);
        assertEquals(pawnPos, figureListener.lastActivated);
    }

    @Test
    void testOnFigureSelectedInvalid() {
        // Empty square at (4,4)
        game.onFigureSelected(new CellPosition(4, 4));
        assertEquals(0, figureListener.activatedCount);

        // Black piece at (1,0) when white's turn
        game.onFigureSelected(new CellPosition(1, 0));
        assertEquals(0, figureListener.activatedCount);
    }

    @Test
    void testOnFigureMovedValidAndTeamSwitch() {
        // Select white pawn at (6,0)
        CellPosition from = new CellPosition(6, 0);
        game.onFigureSelected(from);
        // Move to (5,0)
        CellPosition to = new CellPosition(5, 0);
        game.onFigureMoved(to);

        // Moved event
        assertEquals(1, figureListener.movedCount);
        assertEquals(from, figureListener.lastFrom);
        assertEquals(to, figureListener.lastTo);

        // After move, activeTeam is black => selecting a black pawn now is valid
        figureListener.reset();
        game.onFigureSelected(new CellPosition(1, 0));
        assertEquals(1, figureListener.activatedCount);
    }

    @Test
    void testOnFigureMovedInvalid() {
        // Select white pawn at (6,0)
        game.onFigureSelected(new CellPosition(6, 0));
        // Attempt invalid move (not in trajectory)
        game.onFigureMoved(new CellPosition(4, 4));
        assertEquals(0, figureListener.movedCount);
    }

    @Test
    void testNoStatusEventOnNormalMove() {
        game.onFigureSelected(new CellPosition(6, 0));
        game.onFigureMoved(new CellPosition(5, 0));
        assertEquals(0, statusListener.checkCount);
        assertEquals(0, statusListener.drawCount);
        assertEquals(0, statusListener.winCount);
    }

    // --- helper listeners ---

    static class TestFigureListener implements FigureActionListener {
        int activatedCount = 0, movedCount = 0;
        chess.CellPosition lastActivated, lastFrom, lastTo;

        @Override
        public void figureActivated(FigureActivatedEvent e) {
            activatedCount++;
            lastActivated = e.getFigure().getCell().getPosition();
        }

        @Override
        public void figureMoved(FigureMovedEvent e) {
            movedCount++;
            lastFrom = e.getFrom().getPosition();
            lastTo = e.getTo().getPosition();
        }

        @Override public void figureDeactivated(chess.events.FigureClearSelectedEvent e) {}

        /** Обнуляет все счётчики и сохранённые позиции */
        public void reset() {
            activatedCount = 0;
            movedCount = 0;
            lastActivated = lastFrom = lastTo = null;
        }
    }

    static class TestGameStatusListener implements GameStatusActionListener {
        int checkCount = 0, drawCount = 0, winCount = 0;

        @Override
        public void teamHasCheck(GameStatusCheckEvent e) { checkCount++; }

        @Override
        public void gameFinishInDraw(GameStatusDrawEvent e) { drawCount++; }

        @Override
        public void gameFinishWithWinner(GameStatusWinnerEvent e) { winCount++; }

    }
}



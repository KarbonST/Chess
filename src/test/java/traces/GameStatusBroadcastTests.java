package traces;
import chess.*;
import chess.events.*;
import chess.Figures.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GameStatusBroadcastTest {

    private Game game;
    private TestGameStatusListener statusListener;

    @BeforeEach
    void setUp() throws Exception {
        game = new Game();
        // мы не вызываем game.start(), будем настраивать всё вручную
        statusListener = new TestGameStatusListener();
        game.addGameFinishActionListener(statusListener);
    }

    /**
     * Сценарий «игра продолжается»: на доске только два короля, ход не завершается.
     */
    @Test
    void testNoEventWhenGameContinues() throws Exception {
        Board b = new Board();
        Team white = new Team(Color.WHITE);
        Team black = new Team(Color.BLACK);

        // Белый король e1
        King wk = new King(white);
        b.getCellByPosition(new CellPosition(7, 4)).setFigure(wk);
        white.addFigure(wk);
        // Чёрный король e8
        King bk = new King(black);
        b.getCellByPosition(new CellPosition(0, 4)).setFigure(bk);
        black.addFigure(bk);

        injectGameState(b, white, black);

        // Меняем ход (будет проверка, но ни CHECK ни DRAW ни WIN не сработают)
        invokeChangeTeam();
        assertEquals(0, statusListener.checkCount);
        assertEquals(0, statusListener.drawCount);
        assertEquals(0, statusListener.winCount);
    }

    /**
     * Сценарий «шах» (CHECK): белый король на e1, белая ладья на e2, чёрный король на e4.
     * Ход перейдёт к чёрным, определится CHECK и разошлётся teamHasCheck.
     */
    @Test
    void testCheckBroadcast() throws Exception {
        Board b = new Board();
        Team white = new Team(Color.WHITE);
        Team black = new Team(Color.BLACK);

        King wk = new King(white);
        b.getCellByPosition(new CellPosition(7, 4)).setFigure(wk);
        white.addFigure(wk);

        Rook wr = new Rook(white);
        b.getCellByPosition(new CellPosition(6, 4)).setFigure(wr);
        white.addFigure(wr);

        King bk = new King(black);
        b.getCellByPosition(new CellPosition(4, 4)).setFigure(bk);
        black.addFigure(bk);

        injectGameState(b, white, black);
        invokeChangeTeam();

        assertEquals(1, statusListener.checkCount, "Должен сработать CHECK");
        assertEquals(0, statusListener.drawCount);
        assertEquals(0, statusListener.winCount);
    }

    /**
     * Сценарий «мат» (CHECKMATE): простой угол – белый король h1, белая ладья g1, белая ладья h2,
     * чёрный король h8. Ход чёрных сразу мат.
     */
    @Test
    void testCheckmateBroadcast() throws Exception {
        Board b = new Board();
        Team white = new Team(Color.WHITE);
        Team black = new Team(Color.BLACK);

        // бьём все вокруг чёрного короля
        King wk = new King(white);
        b.getCellByPosition(new CellPosition(7,7)).setFigure(wk); white.addFigure(wk);
        Rook wr1 = new Rook(white);
        b.getCellByPosition(new CellPosition(6,6)).setFigure(wr1); white.addFigure(wr1);
        Rook wr2 = new Rook(white);
        b.getCellByPosition(new CellPosition(6,7)).setFigure(wr2); white.addFigure(wr2);

        King bk = new King(black);
        b.getCellByPosition(new CellPosition(0,7)).setFigure(bk); black.addFigure(bk);

        injectGameState(b, white, black);
        invokeChangeTeam();

        assertEquals(0, statusListener.checkCount);
        assertEquals(0, statusListener.drawCount);
        assertEquals(1, statusListener.winCount, "Должен сработать CHECKMATE");
    }

    /**
     * Сценарий «пат» (STALEMATE): белый король b2, белая ладья a3, чёрный король a1.
     */
    @Test
    void testStalemateBroadcast() throws Exception {
        Board b = new Board();
        Team white = new Team(Color.WHITE);
        Team black = new Team(Color.BLACK);

        King wk = new King(white);
        b.getCellByPosition(new CellPosition(2,5)).setFigure(wk); white.addFigure(wk);
        Queen wr = new Queen(white);
        b.getCellByPosition(new CellPosition(2,3)).setFigure(wr); white.addFigure(wr);

        King bk = new King(black);
        b.getCellByPosition(new CellPosition(0,4)).setFigure(bk); black.addFigure(bk);

        injectGameState(b, white, black);
        invokeChangeTeam();

        assertEquals(0, statusListener.checkCount);
        assertEquals(1, statusListener.drawCount,  "Должен сработать STALEMATE→draw");
        assertEquals(0, statusListener.winCount);
    }

    // === Вспомогательные методы и слушатель ===

    /** Простейшим рефлексивным инжектом подменяем board, teams и judge внутри Game */
    private void injectGameState(Board b, Team white, Team black) throws Exception {
        // board
        Field fBoard = Game.class.getDeclaredField("board");
        fBoard.setAccessible(true);
        fBoard.set(game, b);
        // teams
        Field fTeams = Game.class.getDeclaredField("teams");
        fTeams.setAccessible(true);
        @SuppressWarnings("unchecked")
        var teams = (java.util.List<Team>)fTeams.get(game);
        teams.clear();
        teams.add(white);
        teams.add(black);
        // active/inactive
        Field fActive   = Game.class.getDeclaredField("activeTeam");
        Field fInactive = Game.class.getDeclaredField("inactiveTeam");
        fActive.setAccessible(true);
        fInactive.setAccessible(true);
        fActive.set(game, white);
        fInactive.set(game, black);
        // judge
        Field fJudge = Game.class.getDeclaredField("judge");
        fJudge.setAccessible(true);
        fJudge.set(game, new Judge(b));
    }

    /** Вызывает приватный метод changeTeam() в Game */
    private void invokeChangeTeam() throws Exception {
        Method m = Game.class.getDeclaredMethod("changeTeam");
        m.setAccessible(true);
        m.invoke(game);
    }

    static class TestGameStatusListener implements GameStatusActionListener {
        int checkCount, drawCount, winCount;
        @Override public void teamHasCheck(GameStatusCheckEvent e)       { checkCount++; }
        @Override public void gameFinishInDraw(GameStatusDrawEvent e)    { drawCount++; }
        @Override public void gameFinishWithWinner(GameStatusWinnerEvent e) { winCount++; }
    }
}


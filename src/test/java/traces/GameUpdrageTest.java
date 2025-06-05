package traces;

import model.Board;
import model.Cell;
import model.CellPosition;
import model.Figures.Figure;
import model.Figures.FiguresFactory;
import model.Figures.FiguresTypes;
import model.Game;
import model.Team;
import model.events.FigureUpgradedListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class GameUpgradeTest {

    private Game game;
    private TestUpgradeListener upgradeListener;

    // На какой клетке будем тестировать пешку (белую)
    private static final CellPosition WHITE_PAWN_POS = new CellPosition(6, 0);
    // Предположим, что upgrade() для пешки создаёт фигуру типа WIZARD
    // и у неё отнимаются, скажем, 1 жизнь от изначального количества
    // (у пешки по умолчанию lives = 2, после апгрейда – 1).
    // (значения, конечно, должны соответствовать вашей реализации в Pawn)

    @BeforeEach
    void setUp() {
        game = new Game();
        game.start();

        // Подпишемся на событие апгрейда
        upgradeListener = new TestUpgradeListener();
        game.addFigureUpgradedListener(upgradeListener);

        // Убедимся, что белая пешка стоит в заданной клетке
        Cell whitePawnCell = game.getBoard().getCellByPosition(WHITE_PAWN_POS);
        Figure whitePawn = whitePawnCell.getFigure();
        assertNotNull(whitePawn, "На старте в (6,0) должна стоять белая пешка");
        assertEquals(FiguresTypes.PAWN, whitePawn.getFigureType());
        assertEquals(Color.WHITE, whitePawn.getTeam().getColor());
    }

    /**
     * 1) Проверяем, что апгрейд пешки работает как ожидается:
     *    - тип фигуры меняется на «волшебник» (Wizard).
     *    - количество жизней уменьшается на upgradeDamage().
     *    - флаг команды «апгрейд уже совершен» выставлен.
     *    - событие апгрейда сработало ровно один раз.
     */
    @Test
    void testPawnUpgradeSuccess() {
        // Выбираем белую пешку:
        game.onFigureSelected(WHITE_PAWN_POS);

        // Запомним старые жизни и тип
        Figure pawnBefore = game.getBoard().getCellByPosition(WHITE_PAWN_POS).getFigure();
        int oldLives   = pawnBefore.getLives();
        FiguresTypes oldType = pawnBefore.getFigureType();
        assertEquals(FiguresTypes.PAWN, oldType);

        // Выполняем апгрейд
        game.upgradeFigure();

        // Проверим, что событие апгрейда сработало ровно 1 раз
        assertEquals(1, upgradeListener.count, "Должно быть ровно 1 событие апгрейда");

        // В клетке должен быть новый объект фигуры (Wizard)
        Figure upgraded = game.getBoard().getCellByPosition(WHITE_PAWN_POS).getFigure();
        assertNotNull(upgraded, "После апгрейда в клетке должна стоять улучшенная фигура");
        assertNotEquals(oldType, upgraded.getFigureType(), "Тип фигуры должен измениться");
        assertEquals(FiguresTypes.WIZARD, upgraded.getFigureType(), "Тип должен стать WIZARD");

        // Жизни уменьшились на upgradeDamage()
        int expectedLives = oldLives - pawnBefore.getUpgradeDamage();
        assertEquals(expectedLives, upgraded.getLives(),
                "Количество жизней после апгрейда должно уменьшиться на upgradeDamage()");

        // Проверим, что у команды выставился флаг «апгрейд совершен»
        Team whiteTeam = upgraded.getTeam();
        assertTrue(whiteTeam.isUpgraded(), "Флаг команды isUpgraded должен быть true");

        // Если снова вызвать upgradeFigure(), изменений уже не будет:
        upgradeListener.count = 0;
        game.onFigureSelected(WHITE_PAWN_POS); // снова выбрать ранее апгрейженную фигуру
        game.upgradeFigure();

        // Никакого второго события не должно быть
        assertEquals(0, upgradeListener.count, "При повторном апгрейде событие не должно срабатывать");

        // Тип и жизни должны остаться прежними
        Figure still = game.getBoard().getCellByPosition(WHITE_PAWN_POS).getFigure();
        assertEquals(FiguresTypes.WIZARD, still.getFigureType(), "При повторном апгрейде тип не меняется");
        assertEquals(expectedLives, still.getLives(), "При повторном апгрейде жизни не меняются");
    }

    /**
     * 2) Если активная фигура — не пешка (например, конь), апгрейд не должен проходить:
     *    - никакой апгрейд не срабатывает,
     *    - команда не получает флаг isUpgraded,
     *    - событий апгрейда не генерируется.
     */
    @Test
    void testUpgradeWhenNotPawnDoesNothing() {
        // Выберем, скажем, белого коня (по стандартной расстановке он на (7,1))
        CellPosition knightPos = new CellPosition(7, 1);
        game.onFigureSelected(knightPos);

        Figure knightBefore = game.getBoard().getCellByPosition(knightPos).getFigure();
        assertEquals(FiguresTypes.KNIGHT, knightBefore.getFigureType());

        // Попытка апгрейда
        game.upgradeFigure();

        // Событий апгрейда не должно быть
        assertEquals(0, upgradeListener.count, "Никакое событие апгрейда не должно сработать");

        // Тип фигуры остался прежним (конь)
        Figure knightAfter = game.getBoard().getCellByPosition(knightPos).getFigure();
        assertEquals(FiguresTypes.KNIGHT, knightAfter.getFigureType(),
                "Тип фигуры не должен измениться");
        assertFalse(knightAfter.getTeam().isUpgraded(),
                "Флаг команды isUpgraded не должен выставиться при попытке апгрейда не-пешки");
    }

    /**
     * 3) Ещё проверим, что если ни одна фигура не выбрана (activeFigure = null),
     *    вызов upgradeFigure() тоже ничего не делает.
     */
    @Test
    void testUpgradeWithNoActiveFigureDoesNothing() {
        // activeFigure ещё не назначена → сразу вызвать upgradeFigure()
        game.upgradeFigure();
        assertEquals(0, upgradeListener.count, "Без выбранной фигуры событие не срабатывает");
    }

    // ────────────────────────────────────────────────────────────────────
    // Вспомогательный «слушатель» для подсчёта апгрейдов
    // ────────────────────────────────────────────────────────────────────
    private static class TestUpgradeListener implements FigureUpgradedListener {
        int count = 0;
        @Override
        public void upgradedFigure() {
            count++;
        }
    }
}

package traces;

import model.Cell;
import model.CellPosition;
import model.Direction;
import model.Figures.Figure;
import model.Figures.FiguresTypes;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    /** Простая «заглушка» для Figure, чтобы тестировать setFigure/unsetFigure */
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
        public Figure cloneFigure(){
            return new DummyFigure(this.team);
        }
    }

    private Team team;
    private CellPosition position;
    private Cell cell;

    @BeforeEach
    void setUp() {
        team     = new Team(Color.WHITE);
        position = new CellPosition(2, 3);
        cell     = new Cell(position);
    }

    @Test
    void testConstructor() {
        assertEquals(position, cell.getPosition());
        assertTrue(cell.getNeighbours().isEmpty(), "Neighbours should start empty");
        assertNull(cell.getFigure(), "No figure initially");
    }

    @Test
    void testNeighboursSingleAndNull() {
        // Соседа пока нет
        assertNull(cell.getNeighbour(Direction.NORTH), "No neighbour yet");

        // Добавляем одного соседа
        Cell n = new Cell(new CellPosition(1, 3));
        cell.setNeighbour(n, Direction.NORTH);

        assertSame(n, cell.getNeighbour(Direction.NORTH));
        assertEquals(1, cell.getNeighbours().size());

        // getNeighbour для другого направления по-прежнему null
        assertNull(cell.getNeighbour(Direction.SOUTH), "Only NORTH was set");
    }

    @Test
    void testNeighboursMultipleDirections() {
        Cell n  = new Cell(new CellPosition(1, 3));
        Cell se = new Cell(new CellPosition(3, 4));
        Cell w  = new Cell(new CellPosition(2, 2));

        cell.setNeighbour(n,  Direction.NORTH);
        cell.setNeighbour(se, Direction.SOUTHEAST);
        cell.setNeighbour(w,  Direction.WEST);

        assertEquals(3, cell.getNeighbours().size());
        assertSame(n,  cell.getNeighbour(Direction.NORTH));
        assertSame(se, cell.getNeighbour(Direction.SOUTHEAST));
        assertSame(w,  cell.getNeighbour(Direction.WEST));
    }

    @Test
    void testSetNeighbourDoesNotOverwrite() {
        Cell first  = new Cell(new CellPosition(1, 3));
        Cell second = new Cell(new CellPosition(0, 3));

        cell.setNeighbour(first, Direction.NORTH);
        // Повторно добавляем другого соседа в том же направлении
        cell.setNeighbour(second, Direction.NORTH);

        // Ожидаем, что первый остался, а второй игнорируется
        assertSame(first, cell.getNeighbour(Direction.NORTH));
        assertNotSame(second, cell.getNeighbour(Direction.NORTH));
        assertEquals(1, cell.getNeighbours().size());
    }

    @Test
    void testSetFigure() {
        DummyFigure figure = new DummyFigure(team);
        assertNull(figure.getCell(), "Figure initially has no cell");

        cell.setFigure(figure);

        assertSame(figure, cell.getFigure());
        assertSame(cell, figure.getCell(), "Figure’s cell should be set");
    }

    @Test
    void testUnsetFigure() {
        DummyFigure figure = new DummyFigure(team);
        cell.setFigure(figure);
        assertNotNull(cell.getFigure(), "Figure must be set before unset");

        cell.unsetFigure();

        assertNull(cell.getFigure(),  "Cell should have no figure after unset");
        assertNull(figure.getCell(),  "Figure’s cell should be cleared");
    }
}


package traces;

import model.Board;
import model.Cell;
import model.CellPosition;
import model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testBoardSize() {
        // Должно быть 8×8 = 64 ячейки
        assertEquals(64, board.getCells().size(), "Board should contain 64 cells");
    }

    @Test
    void testGetCellByPositionValid() {
        CellPosition pos = new CellPosition(5, 2);
        Cell cell = board.getCellByPosition(pos);
        assertNotNull(cell, "Cell at (5,2) must exist");
        assertEquals(pos, cell.getPosition());
    }

    @Test
    void testGetCellByPositionInvalid() {
        // Вне диапазона
        assertNull(board.getCellByPosition(new CellPosition(-1, 0)));
        assertNull(board.getCellByPosition(new CellPosition(0, 8)));
        assertNull(board.getCellByPosition(new CellPosition(8, 8)));
    }

    @Test
    void testIsInsideTheBoardBoundaries() {
        assertFalse(Board.isInsideTheBoard(-1, 0));
        assertFalse(Board.isInsideTheBoard(0, -1));
        assertTrue(Board.isInsideTheBoard(0, 0));
        assertTrue(Board.isInsideTheBoard(7, 7));
        assertFalse(Board.isInsideTheBoard(8, 0));
        assertFalse(Board.isInsideTheBoard(7, 8));
    }

    @Test
    void testNeighboursInCenter() {
        Cell center = board.getCellByPosition(new CellPosition(3, 3));
        Map<Direction, Cell> n = center.getNeighbours();
        // У центральной клетки должны быть все 8 направлений
        assertEquals(8, n.size(), "Center should have 8 neighbours");
        for (Direction d : Direction.values()) {
            assertTrue(n.containsKey(d), "Missing direction " + d);
            Cell neighbour = center.getNeighbour(d);
            assertNotNull(neighbour, "Neighbour at " + d + " must not be null");
            // сосед должен располагаться по смещению из enum
            assertEquals(
                    3 + d.getDeltaRow(),
                    neighbour.getPosition().getRow(),
                    "Row mismatch for " + d
            );
            assertEquals(
                    3 + d.getDeltaCol(),
                    neighbour.getPosition().getCol(),
                    "Col mismatch for " + d
            );
        }
    }

    @Test
    void testNeighboursInCorner() {
        Cell corner = board.getCellByPosition(new CellPosition(0, 0));
        Map<Direction, Cell> n = corner.getNeighbours();
        // В верхнем левом углу — только SOUTH, EAST и SOUTHEAST
        assertEquals(3, n.size());
        assertNotNull(n.get(Direction.SOUTH));
        assertNotNull(n.get(Direction.EAST));
        assertNotNull(n.get(Direction.SOUTHEAST));
        // Другие направления должны отсутствовать
        for (Direction d : Direction.values()) {
            if (d != Direction.SOUTH && d != Direction.EAST && d != Direction.SOUTHEAST) {
                assertFalse(n.containsKey(d), "Corner should not have neighbour " + d);
            }
        }
    }

    @Test
    void testNeighboursOnEdge() {
        // Верхняя грань, не угол
        Cell edge = board.getCellByPosition(new CellPosition(0, 4));
        Map<Direction, Cell> n = edge.getNeighbours();
        // Должно быть 5 соседей: E, W, S, SE, SW
        assertEquals(5, n.size());
        assertNotNull(n.get(Direction.EAST));
        assertNotNull(n.get(Direction.WEST));
        assertNotNull(n.get(Direction.SOUTH));
        assertNotNull(n.get(Direction.SOUTHEAST));
        assertNotNull(n.get(Direction.SOUTHWEST));
        // Проверяем, что ни одного «запрещённого» направления нет
        for (Direction d : Direction.values()) {
            if (!(d == Direction.EAST
                    || d == Direction.WEST
                    || d == Direction.SOUTH
                    || d == Direction.SOUTHEAST
                    || d == Direction.SOUTHWEST)) {
                assertFalse(n.containsKey(d), "Edge should not have neighbour " + d);
            }
        }
    }
}


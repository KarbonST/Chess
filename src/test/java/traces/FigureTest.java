package traces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Cell;
import chess.CellPosition;
import chess.Direction;
import chess.Figures.*;
import chess.Team;
import chess.Trajectories.Trajectory;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    private Board board;
    private Team whiteTeam;
    private Team blackTeam;
    private CellPosition centerPos;
    private Cell centerCell;

    @BeforeEach
    void setUp() {
        board = new Board();
        whiteTeam = new Team(Color.WHITE);
        blackTeam = new Team(Color.BLACK);
        centerPos = new CellPosition(3, 3);
        centerCell = board.getCellByPosition(centerPos);
    }

    // Helper to register piece on board
    private void placePiece(Figure piece) {
        centerCell.setFigure(piece);
    }

    // Helper to collect all cells from trajectories
    private Set<CellPosition> allPositions(Figure piece) {
        Set<CellPosition> pos = new HashSet<>();
        for (Trajectory t : piece.getMovementTrajectories()) {
            t.buildTrajectory(piece.getCell());
            for (Cell c : t.getCells()) {
                pos.add(c.getPosition());
            }
        }
        for (Trajectory t : piece.getAttackTrajectories()) {
            t.buildTrajectory(piece.getCell());
            for (Cell c : t.getCells()) {
                pos.add(c.getPosition());
            }
        }
        return pos;
    }

    @Test
    void testKingTrajectories() {
        King king = new King(whiteTeam);
        placePiece(king);

        // King movement = attack (same), eight neighbors
        Set<CellPosition> positions = allPositions(king);
        assertEquals(8, positions.size());
        for (Direction d : Direction.values()) {
            CellPosition expected = new CellPosition(
                    centerPos.getRow() + d.getDeltaRow(),
                    centerPos.getCol() + d.getDeltaCol()
            );
            assertTrue(positions.contains(expected), "Missing " + d);
        }
    }

    @Test
    void testBishopTrajectories() {
        Bishop bishop = new Bishop(whiteTeam);
        placePiece(bishop);

        // Bishop movement and attack are same diagonals; count = 14
        // bishop.getMovementTrajectories only movement, attack separate but same
        // We'll test movement only: 4 diagonals sum distances
        Set<CellPosition> mv = new HashSet<>();
        for (Trajectory t : bishop.getMovementTrajectories()) {
            t.buildTrajectory(bishop.getCell());
            for (Cell c : t.getCells()) mv.add(c.getPosition());
        }
        assertEquals(13, mv.size());
    }

    @Test
    void testRookTrajectories() {
        Rook rook = new Rook(whiteTeam);
        placePiece(rook);

        // Rook movement: 4 directions cardinal, distances sum to 14
        Set<CellPosition> mv = new HashSet<>();
        for (Trajectory t : rook.getMovementTrajectories()) {
            t.buildTrajectory(rook.getCell());
            for (Cell c : t.getCells()) mv.add(c.getPosition());
        }
        assertEquals(14, mv.size());
    }

    @Test
    void testQueenTrajectories() {
        Queen queen = new Queen(whiteTeam);
        placePiece(queen);

        // Queen movement: rook + bishop = 28
        Set<CellPosition> mv = new HashSet<>();
        for (Trajectory t : queen.getMovementTrajectories()) {
            t.buildTrajectory(queen.getCell());
            for (Cell c : t.getCells()) mv.add(c.getPosition());
        }
        assertEquals(27, mv.size());
    }

    @Test
    void testKnightTrajectories() {
        Knight knight = new Knight(whiteTeam);
        placePiece(knight);

        // Knight movement: 8 L-shapes
        Set<CellPosition> mv = new HashSet<>();
        for (Trajectory t : knight.getMovementTrajectories()) {
            t.buildTrajectory(knight.getCell());
            for (Cell c : t.getCells()) mv.add(c.getPosition());
        }
        assertEquals(8, mv.size());

        // Check some known positions
        assertTrue(mv.contains(new CellPosition(1,2)));
        assertTrue(mv.contains(new CellPosition(1,4)));
        assertTrue(mv.contains(new CellPosition(2,1)));
        assertTrue(mv.contains(new CellPosition(4,1)));
        assertTrue(mv.contains(new CellPosition(5,2)));
        assertTrue(mv.contains(new CellPosition(5,4)));
        assertTrue(mv.contains(new CellPosition(2,5)));
        assertTrue(mv.contains(new CellPosition(4,5)));
    }

    @Test
    void testPawnAttackFiltersOwnAndEnemy() {
        // Расставляем пешку в центре
        Pawn pawn = new Pawn(whiteTeam);
        Cell startCell = board.getCellByPosition(new CellPosition(4,3));
        startCell.setFigure(pawn);


        // Проверка движения
        Set<CellPosition> mv = new HashSet<>();
        for (Trajectory t : pawn.getMovementTrajectories()) {
            t.buildTrajectory(pawn.getCell());
            for (Cell c : t.getCells()) mv.add(c.getPosition());
        }
        pawn.moveTo(board.getCellByPosition(new CellPosition(3,3)));

        assertEquals(2, mv.size());
        assertTrue(mv.contains(new CellPosition(2, 3)));
        //assertTrue(mv.contains(new CellPosition(1, 3)));

        // Теперь ставим по диагонали:
        // Северо-запад — своя пешка, Северо-восток — вражеская
        Cell nw = board.getCellByPosition(new CellPosition(2, 2));
        nw.setFigure(new Pawn(whiteTeam));   // своя пешка
        Cell ne = board.getCellByPosition(new CellPosition(2, 4));
        ne.setFigure(new Pawn(blackTeam));   // вражеская пешка

        // Собираем позиции из траекторий атаки
        Set<CellPosition> atk = new HashSet<>();
        for (Trajectory t : pawn.getAttackTrajectories()) {
            t.buildTrajectory(pawn.getCell());
            for (Cell c : t.getCells()) atk.add(c.getPosition());
        }

        // Ожидаем только одну — вражескую
        assertEquals(1, atk.size());
        assertTrue(atk.contains(new CellPosition(2, 4)), "Only the enemy diagonal should remain");
        assertFalse(atk.contains(new CellPosition(2, 2)), "Own piece diagonal must be filtered out");
    }

}



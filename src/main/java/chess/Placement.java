package chess;

import chess.Figures.FiguresFactory;
import chess.Figures.Figure;
import chess.Figures.FiguresTypes;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Расстановка фигур на доске.
 */
public class Placement {
    /**
     * Доска.
     */
    private final Board board;

    /**
     * Фабрика фигур
     */
    private final FiguresFactory figuresFactory;

    /**
     * Команда белых.
     */
    private final Team whiteTeam;
    private final Team blackTeam;

    /**
     * Стартовая расстановка
     */
    private final Map<CellPosition, FigureSpecification> initSetup;


    Placement(Board board){
        this.board = board;
        this.whiteTeam = new Team(Color.WHITE);
        this.blackTeam = new Team(Color.BLACK);
        this.figuresFactory = new FiguresFactory(board);
        this.initSetup = buildInitSetup();
        populateBoard();
    }

    /**
     * Создание списка стартовой расстановки
     */
    private Map<CellPosition, FigureSpecification> buildInitSetup() {
        Map<CellPosition, FigureSpecification> setup = new HashMap<>();

        // Чёрные основные фигуры
        setup.put(new CellPosition(0, 0), new FigureSpecification(FiguresTypes.ROOK,  blackTeam));
        setup.put(new CellPosition(0, 1), new FigureSpecification(FiguresTypes.KNIGHT,blackTeam));
        setup.put(new CellPosition(0, 2), new FigureSpecification(FiguresTypes.BISHOP,blackTeam));
        setup.put(new CellPosition(0, 3), new FigureSpecification(FiguresTypes.QUEEN, blackTeam));
        setup.put(new CellPosition(0, 4), new FigureSpecification(FiguresTypes.KING,  blackTeam));
        setup.put(new CellPosition(0, 5), new FigureSpecification(FiguresTypes.BISHOP,blackTeam));
        setup.put(new CellPosition(0, 6), new FigureSpecification(FiguresTypes.KNIGHT,blackTeam));
        setup.put(new CellPosition(0, 7), new FigureSpecification(FiguresTypes.ROOK,  blackTeam));

        // Чёрные пешки
        for (int col = 0; col < Board.getBoardSize(); col++) {
            setup.put(new CellPosition(1, col), new FigureSpecification(FiguresTypes.PAWN, blackTeam));
        }

        // Белые пешки
        for (int col = 0; col < Board.getBoardSize(); col++) {
            setup.put(new CellPosition(6, col), new FigureSpecification(FiguresTypes.PAWN, whiteTeam));
        }

        // Белые основные фигуры
        setup.put(new CellPosition(7, 0), new FigureSpecification(FiguresTypes.ROOK,  whiteTeam));
        setup.put(new CellPosition(7, 1), new FigureSpecification(FiguresTypes.KNIGHT,whiteTeam));
        setup.put(new CellPosition(7, 2), new FigureSpecification(FiguresTypes.BISHOP,whiteTeam));
        setup.put(new CellPosition(7, 3), new FigureSpecification(FiguresTypes.QUEEN, whiteTeam));
        setup.put(new CellPosition(7, 4), new FigureSpecification(FiguresTypes.KING,  whiteTeam));
        setup.put(new CellPosition(7, 5), new FigureSpecification(FiguresTypes.BISHOP,whiteTeam));
        setup.put(new CellPosition(7, 6), new FigureSpecification(FiguresTypes.KNIGHT,whiteTeam));
        setup.put(new CellPosition(7, 7), new FigureSpecification(FiguresTypes.ROOK,  whiteTeam));

        return setup;
    }


    /**
     * Заселение доски.
     */
    private void populateBoard(){
        for (Map.Entry<CellPosition, FigureSpecification> entry : initSetup.entrySet()) {
            CellPosition pos = entry.getKey();
            FigureSpecification spec = entry.getValue();

            Cell cell = board.getCellByPosition(pos);

            // Создаем фигуру нужного типа и цвета
            Figure figure = figuresFactory.createFigure(spec.getFiguresTypes(), spec.getTeam());

            // Связываем фигуру и ячейку
            cell.setFigure(figure);
            figure.setCell(cell);

            // Регистрируем фигуру в команде
            spec.getTeam().addFigure(figure);
        }
    }

    /**
     * Получить команду белых
     * @return команда белых
     */
    public Team getWhiteTeam() {
        return whiteTeam;
    }

    /**
     * Получить команду черных
     * @return команда черных
     */
    public Team getBlackTeam() {
        return blackTeam;
    }
}

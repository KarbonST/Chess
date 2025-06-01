package model.Figures;

import model.Cell;
import model.Team;

public class UndoableMove {
    private final Figure figure;
    private final Cell cellFrom;
    private final Cell cellTo;
    private final Figure capturedFigure;
    private final Team capturedFigureTeam;
    private final boolean figureHasMoved;

    public UndoableMove(Figure figure, Cell cellTo){
        this.figure = figure;
        this.cellFrom = figure.getCell();
        this.cellTo = cellTo;
        this.figureHasMoved = figure.hasMoved();
        this.capturedFigure = cellTo.getFigure();

        if (capturedFigure != null){
            capturedFigureTeam = capturedFigure.getTeam();
        }
        else {
            capturedFigureTeam = null;
        }
    }

    /**
     * Переместиться
     */
    public void move(){
        // В целевой ячейке была вражеская фигура
        if (capturedFigure != null){
            // Удаляем фигуру
            capturedFigure.die();
        }

        // Переносим фигуру
        figure.unsetCell();
        figure.setCell(cellTo);
        figure.setHasMoved(true);

    }

    /**
     * Отмена перемещения
     */
    public void undo(){
        // Перенос фигуры обратно
        figure.unsetCell();
        figure.setCell(cellFrom);
        figure.setHasMoved(figureHasMoved);

        // Если убитая фигура была, возвращаем её
        if (capturedFigure != null){
            cellTo.setFigure(capturedFigure);
            cellTo.getFigure().setTeam(capturedFigureTeam);
        }

    }
}

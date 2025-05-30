package ui;

import model.Board;
import model.Cell;
import model.CellPosition;
import model.Figures.Figure;
import ui.events.BoardClickListener;
import ui.events.CellClickEvent;
import ui.events.CellClickListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Доска
 */
public class BoardPanel extends JPanel implements CellClickListener{

    /**
     * Список ячеек
     */
    private final CellUI[][] cells = new CellUI[Board.getBoardSize()][Board.getBoardSize()];

    /**
     * Список слушателей клика по доске
     */
    private final List<BoardClickListener> boardClickListeners = new CopyOnWriteArrayList<>();

    /**
     * Доска
     */
    private final Board board;

    public BoardPanel(Board board){
        this.board = board;
        setLayout(new GridLayout(Board.getBoardSize(), Board.getBoardSize()));
        for (int row = 0; row < Board.getBoardSize(); row++){
            for (int col = 0; col < Board.getBoardSize(); col++){
                Color bg = (row+col) % 2 == 0 ? Color.WHITE : Color.GRAY;
                CellUI cell = new CellUI(new CellPosition(row, col), bg);

                // Подписываем доску на события клика по конкретной ячейке
                cell.addCellClickListener(this);

                this.cells[row][col] = cell;
                add(cell);
            }
        }
    }

    /**
     * Отрисовать фигуры на доске
     */
    public void paintFiguresOnBoard(){
        for (int row = 0; row < Board.getBoardSize(); row++){
            for (int col = 0; col < Board.getBoardSize(); col++){
                CellUI cellUI = cells[row][col];
                CellPosition pos = new CellPosition(row, col);
                Cell modelCell = board.getCellByPosition(pos);

                Figure figure = modelCell.getFigure();
                if (figure != null){
                    String color = figure.getTeam().getColor() == Color.WHITE ? "white" : "black";
                    String type  = figure.getFigureType().name().toLowerCase();
                    String path  = "/figures/" + type + "_" + color + ".png";

                    ImageIcon icon = new ImageIcon(getClass().getResource(path));
                    cellUI.setFigureIcon(icon);
                }
                else{
                    cellUI.setFigureIcon(null);
                }
            }
        }
    }

    /**
     * Подсветить клетки
     * @param cellsPositions позиции подсвечиваемых ячеек
     */
    public void highlightCells(List<CellPosition> cellsPositions){
        for (CellPosition pos : cellsPositions) {
            int r = pos.getRow();
            int c = pos.getCol();
            if (r >= 0 && r < Board.getBoardSize() && c >= 0 && c < Board.getBoardSize()) {
                cells[r][c].setBackground(Color.YELLOW);
            }
        }

        repaint();
    }

    /**
     * Подсветить ячейку с активной фигурой
     * @param cellPosition позиция ячейки
     */
    public void highlightCellWithActiveFigure(CellPosition cellPosition){
        int r = cellPosition.getRow();
        int c = cellPosition.getCol();

        if (r >= 0 && r < Board.getBoardSize() && c >= 0 && c < Board.getBoardSize()){
            cells[r][c].setBackground(Color.GREEN);
        }
        repaint();
    }

    /**
     * Убрать подсветку всех клеток
     */
    public void clearHighlights(){
        for (int row = 0; row < Board.getBoardSize(); row++) {
            for (int col = 0; col < Board.getBoardSize(); col++) {
                cells[row][col].setBackground(cells[row][col].getDefaultColor());
            }
        }
        repaint();
    }

    /**
     * Переместить иконку фигуры на доске
     * @param from откуда происходит перенос
     * @param to куда происходит перенос
     */
    public void moveFigure(CellPosition from, CellPosition to){

        CellUI fromCellUI = cells[from.getRow()][from.getCol()];
        CellUI toCellUI   = cells[to.getRow()][to.getCol()];

        ImageIcon icon = fromCellUI.getFigureIcon();

        fromCellUI.setFigureIcon(null);

        toCellUI.setFigureIcon(icon);
    }

    /**
     * Добавить слушателя
     */
    public void addBoardClickListener(BoardClickListener l){
        this.boardClickListeners.add(l);
    }

    /**
     * Удалить слушателя
     */
    public void removeBoardCLickListener(BoardClickListener l){
        this.boardClickListeners.remove(l);
    }


    @Override
    public void cellClicked(CellClickEvent cellClickEvent) {
        CellPosition pos = cellClickEvent.getCellPosition();
        for (var l: boardClickListeners){
            l.cellClicked(pos);
        }
    }
}

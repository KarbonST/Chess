package ui;

import model.Board;
import model.Cell;
import model.CellPosition;
import model.Figures.Figure;
import ui.events.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Доска
 */
public class BoardPanel extends JPanel implements CellClickListener, InfoPanelButtonUpgradeClickListener {

    /**
     * Список ячеек
     */
    private final CellUI[][] cells = new CellUI[Board.getBoardSize()][Board.getBoardSize()];

    /**
     * Список слушателей клика по доске
     */
    private final List<BoardClickListener> boardClickListeners = new CopyOnWriteArrayList<>();

    /**
     * Список слушателей клика по кнопке Upgrade
     */
    private final List<BoardPanelButtonUpgradeClickListener> upgradeClickListeners = new CopyOnWriteArrayList<>();

    /**
     * Доска
     */
    private final Board board;

    /**
     * Позиция активной фигуры
     */
    private CellPosition activeFigurePosition;

    /**
     * Информационная панель
     */
    private InfoPanel infoPanel;

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
     * Задать информационную панель
     * @param infoPanel информационная панель
     */
    public void setInfoPanel(InfoPanel infoPanel){
        this.infoPanel = infoPanel;

        // Подписываемся на события клика по кнопке Upgrade
        if (infoPanel != null){
            infoPanel.addUpgradeClickListener(this);
        }
    }

    /**
     * Получить информационную панель
     */
    public InfoPanel getInfoPanel(){
        return this.infoPanel;
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
                cells[r][c].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
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
            cells[r][c].setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            this.activeFigurePosition = new CellPosition(r,c);
        }

        // Показать информация о фигуре
        if (infoPanel != null){
            Figure figure = board.getCellByPosition(cellPosition).getFigure();
            ImageIcon imageIcon = cells[r][c].getFigureIcon();
            infoPanel.showFigureInfo(figure, imageIcon);
        }

        repaint();
    }

    /**
     * Убрать подсветку всех клеток
     */
    public void clearHighlights(){
        for (int row = 0; row < Board.getBoardSize(); row++) {
            for (int col = 0; col < Board.getBoardSize(); col++) {
                cells[row][col].setBorder(
                        BorderFactory.createLineBorder(cells[row][col].getDefaultColor().darker(), 1)
                );
            }
        }

        // Убрать информацию о фигуре
        if (infoPanel != null){
            infoPanel.clear();
        }

        repaint();
    }

    /**
     * Удалить с ячейки фигуру
     */
    public void deleteFigure(CellPosition deletePos){
        CellUI deleteCell = cells[deletePos.getRow()][deletePos.getCol()];
        deleteCell.setFigureIcon(null);
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
     * Добавить слушателя клика по доске
     */
    public void addBoardClickListener(BoardClickListener l){
        this.boardClickListeners.add(l);
    }

    /**
     * Удалить слушателя клика по доске
     */
    public void removeBoardCLickListener(BoardClickListener l){
        this.boardClickListeners.remove(l);
    }

    /**
     * Добавить слушателя клика по кнопке Upgrade
     */
    public void addUpgradeButtonClickListener(BoardPanelButtonUpgradeClickListener l){
        this.upgradeClickListeners.add(l);
    }

    /**
     * Удалить слушателя клика по кнопке Upgrade
     */
    public void removeUpgradeButtonClickListener(BoardPanelButtonUpgradeClickListener l){
        this.upgradeClickListeners.remove(l);
    }

    /**
     * Произошел апгрейд фигуры в модели
     */
    public void upgradeFigure(){
        // Меняем иконку
        CellUI cellUI = cells[this.activeFigurePosition.getRow()][this.activeFigurePosition.getCol()];
        cellUI.setFigureIcon(null);

        Cell modelCell = board.getCellByPosition(this.activeFigurePosition);

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


    @Override
    public void cellClicked(CellClickEvent cellClickEvent) {
        CellPosition pos = cellClickEvent.getCellPosition();
        for (var l: boardClickListeners){
            l.cellClicked(pos);
        }
    }


    @Override
    public void buttonUpgradeClicked() {
        for (var l: upgradeClickListeners){
            l.buttonUpgradeClicked();
        }
    }
}

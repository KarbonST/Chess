package ui;

import model.Board;
import model.Cell;
import model.CellPosition;
import model.Figures.Figure;

import javax.swing.*;
import java.awt.*;

/**
 * Доска
 */
public class BoardPanel extends JPanel {

    /**
     * Список ячеек
     */
    private final CellUI[][] cells = new CellUI[Board.getBoardSize()][Board.getBoardSize()];

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
}

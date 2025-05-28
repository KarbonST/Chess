package ui;

import model.Board;
import model.CellPosition;

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

    public BoardPanel(){
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
}

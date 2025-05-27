package ui;

import javax.swing.*;
import model.CellPosition;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Представление ячейки
 */
public class CellUI extends JPanel {

    /**
     * Позиция
     */
    private final CellPosition cellPosition;

    /**
     * Стандартный цвет ячейки
     */
    private final Color defaultColor;

    /**
     * Список слушателей
     */


    /**
     * Иконка фигуры
     */
    private ImageIcon figureIcon;



    public CellUI(CellPosition cellPosition, Color defaultColor){
        this.cellPosition = cellPosition;
        this.defaultColor = defaultColor;
        this.figureIcon = null;

        setBackground(defaultColor);
        setPreferredSize(new Dimension(64, 64));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO событие о том, что был совершен клик по ячейке

            }
        });
    }
}

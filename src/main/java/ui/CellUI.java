package ui;

import javax.swing.*;
import model.CellPosition;
import ui.events.CellClickEvent;
import ui.events.CellClickListener;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private final List<CellClickListener> clickListeners = new CopyOnWriteArrayList<>();

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
                fireCellClicked(); // Рассылка события клика
            }
        });
    }

    /**
     * Добавить слушателя
     */
    public void addCellClickListener(CellClickListener l){
        this.clickListeners.add(l);
    }

    /**
     * Удалить слушателя
     */
    public void removeCellCLickListener(CellClickListener l){
        this.clickListeners.remove(l);
    }

    /**
     * Рассылка события слушателям
     */
    public void fireCellClicked(){
        CellClickEvent event = new CellClickEvent(this, getCellPosition());
        for (var l: this.clickListeners){
            l.cellClicked(event);
        }
    }

    /**
     * Получить иконку фигуры
     * @return иконка фигуры
     */
    public ImageIcon getFigureIcon(){
        return this.figureIcon;
    }

    /**
     * Задать иконку фигуры
     * @param figureIcon иконка фигуры
     */
    public void setFigureIcon(ImageIcon figureIcon){
        this.figureIcon = figureIcon;
    }

    /**
     * Получить позицию ячейки
     * @return позиция ячейки
     */
    public CellPosition getCellPosition(){
        return this.cellPosition;
    }
}

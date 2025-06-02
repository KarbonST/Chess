package ui.events;

import model.CellPosition;

import java.util.EventObject;

public class ButtonUpgradeClickEvent extends EventObject {

    /**
     * Позиция фигуры, которую нужно проабгрейдить
     */
    private final CellPosition figurePosition;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param figurePosition позиция фигуры
     * @throws IllegalArgumentException if source is null
     */
    public ButtonUpgradeClickEvent(Object source, CellPosition figurePosition) {
        super(source);
        this.figurePosition = figurePosition;
    }

    /**
     * Получить позицию фигуры
     */
    public CellPosition getFigurePosition(){
        return this.figurePosition;
    }
}

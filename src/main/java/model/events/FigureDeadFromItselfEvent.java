package model.events;

import model.CellPosition;
import model.Figures.Figure;

import java.util.EventObject;

public class FigureDeadFromItselfEvent extends EventObject {

    /**
     * Позиция фигуры
     */
    private final CellPosition figurePosition;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param figurePosition figure position
     * @throws IllegalArgumentException if source is null
     */
    public FigureDeadFromItselfEvent(Object source, CellPosition figurePosition) {
        super(source);
        this.figurePosition = figurePosition;
    }

    /**
     * Получить позицию фигуры
     * @return позиция фигуры
     */
    public CellPosition getFigurePosition(){
        return this.figurePosition;
    }
}

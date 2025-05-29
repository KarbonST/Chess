package ui.events;

import model.CellPosition;

import java.util.EventObject;

public class CellClickEvent extends EventObject {

    /**
     * Позиция ячейки
     */
    private final CellPosition cellPosition;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CellClickEvent(Object source, CellPosition cellPosition) {
        super(source);
        this.cellPosition = cellPosition;
    }

    /**
     * Получить позицию ячейки
     */
    public CellPosition getCellPosition(){
        return this.cellPosition;
    }
}

package model.events;

import model.Figures.Figure;

public class FigureClearSelectedEvent extends FigureActionEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param figure figure
     * @throws IllegalArgumentException if source is null
     */
    public FigureClearSelectedEvent(Object source, Figure figure) {
        super(source, figure);
    }
}

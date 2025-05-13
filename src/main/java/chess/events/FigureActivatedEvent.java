package chess.events;

import chess.Figures.Figure;

public class FigureActivatedEvent extends FigureActionEvent{

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FigureActivatedEvent(Object source, Figure figure) {
        super(source, figure);
    }
}

package chess.events;

import chess.Cell;
import chess.Figures.Figure;

import java.util.EventObject;
import java.util.List;

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

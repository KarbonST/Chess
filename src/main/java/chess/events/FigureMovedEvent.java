package chess.events;

import chess.Cell;
import chess.Figures.Figure;

public class FigureMovedEvent extends FigureActionEvent{

    /**
     * Ячейка из которой фигура переместилась
     */
    private final Cell from;

    /**
     * Ячейка куда фигура переместилась
     */
    private final Cell to;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FigureMovedEvent(Object source, Figure figure, Cell from, Cell to) {
        super(source, figure);
        this.from = from;
        this.to = to;
    }

    public Cell getFrom(){
        return this.from;
    }

    public Cell getTo(){
        return this.to;
    }
}

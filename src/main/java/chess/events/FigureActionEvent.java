package chess.events;

import chess.Figures.Figure;

import java.util.EventObject;

public class FigureActionEvent extends EventObject {

    /**
     * Фигура
     */
    private final Figure figure;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param figure figure
     * @throws IllegalArgumentException if source is null
     */
    public FigureActionEvent(Object source, Figure figure) {
        super(source);
        this.figure = figure;
    }

    /**
     * Получить фигуру
     * @return фигура
     */
    public Figure getFigure(){
        return this.figure;
    }
}

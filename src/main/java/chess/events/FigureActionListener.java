package chess.events;

import java.util.EventListener;

public interface FigureActionListener extends EventListener {

    /**
     * Фигуру выбрали в качестве активной
     * @param figureActivatedEvent объект события активации фигуры
     */
    void figureActivated(FigureActivatedEvent figureActivatedEvent);

    /**
     * Фигура перемещается
     * @param figureMovedEvent объект события перемещения фигуры
     */
    void figureMoved(FigureMovedEvent figureMovedEvent);

}

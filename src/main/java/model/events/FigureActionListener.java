package model.events;

import java.util.EventListener;

public interface FigureActionListener extends EventListener {

    /**
     * Фигуру выбрали в качестве активной
     * @param figureActivatedEvent объект события активации фигуры
     */
    void figureActivated(FigureActivatedEvent figureActivatedEvent);

    /**
     * Фигуру деактивировали
     */
    void figureDeactivated(FigureClearSelectedEvent figureClearSelectedEvent);

    /**
     * Фигура перемещается
     * @param figureMovedEvent объект события перемещения фигуры
     */
    void figureMoved(FigureMovedEvent figureMovedEvent);

}

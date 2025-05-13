package chess.events;

import java.util.EventListener;

public interface FigureActionListener extends EventListener {

    /**
     * Фигуру выбрали в качестве активной
     * @param figureActionEvent объект события активации фигуры
     */
    void figureActivated(FigureActionEvent figureActionEvent);

    /**
     * Фигура перемещается
     * @param figureActionEvent объект события перемещения фигуры
     */
    void figureMoved(FigureActionEvent figureActionEvent);

}

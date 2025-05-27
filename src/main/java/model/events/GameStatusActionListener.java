package model.events;

import java.util.EventListener;

public interface GameStatusActionListener extends EventListener {

    /**
     * Игра завершилась ничьей
     */
    void gameFinishInDraw(GameStatusDrawEvent gameFinishDrawEvent);

    /**
     * Игра завершилась победой одной из команд
     */
    void gameFinishWithWinner(GameStatusWinnerEvent gameFinishWinnerEvent);

    /**
     * Команде объявлен шах
     */
    void teamHasCheck(GameStatusCheckEvent gameStatusCheckEvent);
}

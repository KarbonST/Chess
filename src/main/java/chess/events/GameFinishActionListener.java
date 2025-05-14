package chess.events;

import java.util.EventListener;

public interface GameFinishActionListener extends EventListener {

    /**
     * Игра завершилась ничьей
     */
    void gameFinishInDraw(GameFinishDrawEvent gameFinishDrawEvent);

    /**
     * Игра завершилась победой одной из команд
     */
    void gameFinishWithWinner(GameFinishWinnerEvent gameFinishWinnerEvent);
}

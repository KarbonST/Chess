package chess.events;

import chess.Game;

public class GameFinishDrawEvent extends GameFinishActionEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameFinishDrawEvent(Object source) {
        super(source);
    }
}

package chess.events;

import chess.Game;

import java.util.EventObject;

public class GameFinishActionEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameFinishActionEvent(Object source) {
        super(source);
    }

    /**
     * Получить игру
     */
    public Game getGame(){
        return (Game) getSource();
    }
}

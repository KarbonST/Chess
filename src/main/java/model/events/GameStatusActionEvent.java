package model.events;

import model.Game;

import java.util.EventObject;

public class GameStatusActionEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameStatusActionEvent(Object source) {
        super(source);
    }

    /**
     * Получить игру
     */
    public Game getGame(){
        return (Game) getSource();
    }
}

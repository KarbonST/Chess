package chess.events;

import chess.Game;

import java.util.EventObject;

public class GameFinishActionEvent extends EventObject {

    /**
     * Игра
     */
    private final Game game;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param game game
     * @throws IllegalArgumentException if source is null
     */
    public GameFinishActionEvent(Object source, Game game) {
        super(source);
        this.game = game;
    }

    /**
     * Получить игру
     */
    public Game getGame(){
        return this.game;
    }
}

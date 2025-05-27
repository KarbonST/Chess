package model.events;

public class GameStatusDrawEvent extends GameStatusActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameStatusDrawEvent(Object source) {
        super(source);
    }
}

package model.events;

import model.Team;

public class GameStatusCheckEvent extends GameStatusActionEvent{

    /**
     * Команда, которой объявлен шах
     */
    private final Team checkTeam;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param checkTeam team with check
     * @throws IllegalArgumentException if source is null
     */
    public GameStatusCheckEvent(Object source, Team checkTeam) {
        super(source);
        this.checkTeam = checkTeam;
    }

    /**
     * Получить команду, которой объявлен шах
     * @return команда, которой объявлен шах
     */
    public Team getCheckTeam() {
        return this.checkTeam;
    }
}

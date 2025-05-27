package model.events;

import model.Team;

public class GameStatusWinnerEvent extends GameStatusActionEvent {

    /**
     * Команда победитель
     */
    private final Team winnerTeam;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameStatusWinnerEvent(Object source, Team winnerTeam) {
        super(source);
        this.winnerTeam = winnerTeam;
    }

    /**
     * Получить команду победителя
     */
    public Team getWinnerTeam(){
        return this.winnerTeam;
    }
}

package chess.events;

import chess.Game;
import chess.Team;

public class GameFinishWinnerEvent extends GameFinishActionEvent{

    /**
     * Команда победитель
     */
    private final Team winnerTeam;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param game game
     * @throws IllegalArgumentException if source is null
     */
    public GameFinishWinnerEvent(Object source, Game game, Team winnerTeam) {
        super(source, game);
        this.winnerTeam = winnerTeam;
    }

    /**
     * Получить команду победителя
     */
    public Team getWinnerTeam(){
        return this.winnerTeam;
    }
}

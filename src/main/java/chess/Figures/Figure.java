package chess.Figures;
import chess.Team;
/**
 * Фигура.
 */
public class Figure {

    /**
     * Команда.
     */
    private Team team;

    /**
     * Получить команду.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Задать команду.
     */
    public void setTeam(Team team) {
        this.team = team;
    }
}

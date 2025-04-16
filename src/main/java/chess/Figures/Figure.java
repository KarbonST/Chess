package chess.Figures;
import chess.Team;
/**
 * Фигура.
 */
public abstract class Figure {

    /**
     * Команда.
     */
    protected Team team;

    /**
     * Получить команду.
     */
    public Team getTeam() {
        return this.team;
    }

    Figure(Team team){
        this.team = team;
    }

    /**
     * Вернуть тип фигуры
     * @return тип фигуры
     */
    public abstract FiguresTypes getFigureType();
}

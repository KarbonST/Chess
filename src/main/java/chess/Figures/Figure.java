package chess.Figures;
import chess.Cell;
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
     * Ячейка
     */
    protected Cell cell;

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

    /**
     * Задать ячейку фигуре
     * @param cell ячейка
     */
    public void setCell(Cell cell){
        this.cell = cell;
    }

    /**
     * Удалить ячейку фигуры
     */
    public void deleteCell(){
        this.cell = null;
    }
}

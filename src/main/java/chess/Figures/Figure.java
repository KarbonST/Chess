package chess.Figures;
import chess.Cell;
import chess.CellPosition;
import chess.Direction;
import chess.Team;
import chess.Trajectories.Trajectory;
import java.util.List;

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
     * Направления движения фигуры
     */
    protected List<Direction> movementDirections;

    /**
     * Направления атаки фигуры
     */
    protected List<Direction> attackDirections;

    /**
     * Траектории атаки фигуры
     */
    protected List<Trajectory> attackTrajectories;

    /**
     * Траектория движения фигуры
     */
    protected List<Trajectory> movementTrajectories;

    /**
     * Радиус траектории движения
     */
    protected int movementRadius;

    /**
     * Радиус траектории атаки
     */
    protected int attackRadius;


    /**
     * Смещение за один шаг
     */
    protected int[][] shiftPerStep;

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

    /**
     * Получить траектории движения фигуры
     * @return траектория движения фигуры
     */
    public List<Trajectory> getMovementTrajectories(){
        return this.movementTrajectories;
    }

    /**
     * Получить траектории атаки фигуры
     * @return траектория атаки фигуры
     */
    public List<Trajectory> getAttackTrajectories(){
        return this.attackTrajectories;
    }

    /**
     * Получить радиус траектории движения
     * @return радиус траектории движения
     */
    public int getMovementRadius(){
        return this.movementRadius;
    }

    /**
     * Получить радиус траектории атаки
     * @return радиус траектории атаки
     */
    public int getAttackRadius(){
        return this.attackRadius;
    }

    /**
     * Задать радиус траектории движения
     * @param movementRadius радиус движения
     */
    public void setMovementRadius(int movementRadius){
        this.movementRadius = movementRadius;
    }

    /**
     * Задать радиус траектории атаки
     * @param attackRadius радиус атаки
     */
    public void setAttackRadius(int attackRadius){
        this.attackRadius = attackRadius;
    }

    /**
     * Получить позицию фигуры
     * @return позиция фигуры
     */
    public CellPosition getFigurePosition(){
        if (this.cell != null){
            return this.cell.getPosition();
        }
        return null;
    }

    /**
     * Получить ячейку фигуры
     * @return ячейка фигуры
     */
    public Cell getCell(){
        return this.cell;
    }

}

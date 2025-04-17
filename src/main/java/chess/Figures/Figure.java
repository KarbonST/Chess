package chess.Figures;
import chess.Cell;
import chess.Team;
import chess.Trajectories.AttackTrajectories.AttackTrajectory;
import chess.Trajectories.MovementTrajectories.MovementTrajectory;

import java.util.ArrayList;
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
     * Список траекторий атаки фигуры
     */
    protected final List<AttackTrajectory> attackTrajectories;

    /**
     * Список траекторий движения фигуры
     */
    protected final List<MovementTrajectory> movementTrajectories;

    /**
     * Радиус траектории движения
     */
    protected int movementRadius;

    /**
     * Радиус траектории атаки
     */
    protected int attackRadius;

    /**
     * Получить команду.
     */
    public Team getTeam() {
        return this.team;
    }

    Figure(Team team){
        this.team = team;
        this.attackTrajectories = new ArrayList<>();
        this.movementTrajectories = new ArrayList<>();
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
     * @return траектории движения фигуры
     */
    public List<MovementTrajectory> getMovementTrajectories(){
        return this.movementTrajectories;
    }

    /**
     * Получить траектории атаки фигуры
     * @return траектории атаки фигуры
     */
    public List<AttackTrajectory> getAttackTrajectories(){
        return this.attackTrajectories;
    }

    /**
     * Получить радиус траектории движения
     */
    
}

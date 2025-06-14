package model.Figures;
import model.Cell;
import model.CellPosition;
import model.Direction;
import model.Team;
import model.Trajectories.Trajectory;

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
     * Количество жизней
     */
    protected int lives;

    /**
     * Количество жизней, которые отнимаются при апргрейде
     */
    protected int upgradeDamage;

    /**
     * Может ли фигура "перепрыгивать" другие фигуры
     */
    protected boolean canJumpOver = false;

    /**
     * Ходила ли фигура в течение игры
     */
    protected boolean hasMoved = false;

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
     * Состояние фигуры (заморожена/разморожена)
     */
    protected boolean frozen = false;

    /**
     * Заморозить фигуру
     */
    public void freeze(){
        this.frozen = true;
    }

    /**
     * Разморозить фигуру
     */
    public void unfreeze(){
        this.frozen = false;
    }

    /**
     * Получить состояние фигуры (заморожена/разморожена)
     * @return заморожена ли фигура
     */
    public boolean isFrozen(){
        return this.frozen;
    }

    /**
     * Получить команду.
     */
    public Team getTeam() {
        return this.team;
    }

    protected Figure(Team team){
        this.team = team;
    }

    /**
     * Вернуть тип фигуры
     * @return тип фигуры
     */
    public abstract FiguresTypes getFigureType();

    /**
     * Вернуть фигуру, в которую фигура может сделать Upgrade
     * @return тип фигуры
     */
    public abstract FiguresTypes getUpgradeFigureType();

    /**
     * Задать ячейку фигуре
     * @param cell ячейка
     */
    public void setCell(Cell cell){
        this.cell = cell;
        if (this.cell.getFigure() == null){
            this.cell.setFigure(this);
        }
    }

    /**
     * Удалить ячейку фигуры
     */
    public void unsetCell(){
        Cell currentCell = this.cell;
        this.cell = null;
        if (currentCell.getFigure() == this){
            currentCell.unsetFigure();
        }
    }

    /**
     * Задать команду фигуре
     * @param team команда
     */
    public void setTeam(Team team){
        this.team = team;
        if (!this.team.getFigureList().contains(this)){
            this.team.addFigure(this);
        }
    }

    /**
     * Отвязать команду от фигуры
     */
    public void unsetTeam(){
        Team currentTeam = this.team;
        this.team = null;
        if (currentTeam.getFigureList().contains(this)){
            currentTeam.deleteFigure(this);
        }
    }

    /**
     * Получить количество жизней
     * @return количество жизней
     */
    public int getLives(){
        return this.lives;
    }

    /**
     * Нанесение урона себе
     * @param damage кол-во урона
     */
    public void takeDamage(int damage){
        if (getLives() != Integer.MAX_VALUE){
            this.lives = lives - damage;
        }
    }

    /**
     * Получить урон, который наносится при апргрейде
     * @return урон
     */
    public int getUpgradeDamage(){
        return this.upgradeDamage;
    }

    /**
     * Жива ли фигура
     * @return жива ли фигура
     */
    public boolean isAlive(){
        return getLives() > 0;
    }

    /**
     * Задать количество жизней фигуре
     * @param hp - количество жизней
     */
    public void setLives(int hp){
        this.lives = hp;
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
     * Получить список ячеек из всех траекторий
     * @return список всех доступных позиций для движения
     */
    public List<Cell> getAllCellsFromTrajectories(){
        List<Cell> allCells = new ArrayList<>();

        // Для всех траекторий движения
        for (Trajectory trajectory: getMovementTrajectories()){
            allCells.addAll(trajectory.getCells());
        }

        // Для всех траекторий атаки
        for (Trajectory trajectory: getAttackTrajectories()){
            allCells.addAll(trajectory.getCells());
        }

        return allCells;
    }

    /**
     * Получить список позиций ячеек их всех траекторий
     * @return список позиций доступных ячеек для движения
     */
    public List<CellPosition> getAllCellsPositionsFromTrajectories(){
        List<CellPosition> allCellsPositions = new ArrayList<>();

        // Для всех траекторий движения
        for (Trajectory trajectory: getMovementTrajectories()){
            // Для всех ячеек траектории
            for (Cell cell: trajectory.getCells()){
                allCellsPositions.add(cell.getPosition());
            }
        }

        // Для всех траекторий атаки
        for (Trajectory trajectory: getAttackTrajectories()){
            // Для всех ячеек траектории
            for (Cell cell: trajectory.getCells()){
                allCellsPositions.add(cell.getPosition());
            }
        }

        return allCellsPositions;
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

    /**
     * Может ли фигура перепрыгивать другие фигуры
     */
    public boolean canJumpOver(){
        return this.canJumpOver;
    }

    /**
     * Ходила ли фигура в течение игры
     * @return ходила ли фигура в течение игры
     */
    public boolean hasMoved(){
        return this.hasMoved;
    }

    /**
     * Задать состояние ходила ли фигура в течение игры
     * @param hasMoved двигалась ли фигура в течение игры
     */
    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    /**
     * Переместиться в заданную ячейку
     * @param targetCell целевая ячейка
     * @return последний ход
     */
    public UndoableMove moveTo(Cell targetCell){

        // Фигура заморожена
        if (isFrozen()){
            return null;
        }

        // Объединить траектории в один лист
        List<Trajectory> trajectories = new ArrayList<>(getMovementTrajectories());
        trajectories.addAll(getAttackTrajectories());

        UndoableMove undoableMove = new UndoableMove(this, targetCell);

        // Для всех траекторий движения
        for (Trajectory trajectory: trajectories){
            // Целевая ячейка есть в траектории
            if (trajectory.getCells().contains(targetCell)){
                undoableMove.move();
            }
        }

        takeDamage(1);

        return undoableMove;
    }

    /**
     * Смерть
     */
    public void die(){
        unsetCell();
        unsetTeam();
    }

    public abstract Figure cloneFigure();
}

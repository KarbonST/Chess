package model;

import model.Figures.Figure;
import model.Figures.FiguresTypes;
import model.Trajectories.Trajectory;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * Команда.
 */
public class Team {

    /**
     * Список фигур.
     */
    private final List<Figure> figureList;

    /**
     * Цвет команды
     */
    private final Color color;

    public Team(Color color){
        this.figureList = new ArrayList<>();
        this.color = color;
    }
    /**
     * Добавить фигуру в команду
     * @param figure фигура
     */
    public void addFigure(Figure figure){
        figureList.add(figure);
        if (figure.getTeam() != this){
            figure.setTeam(this);
        }
    }

    /**
     * Удалить фигуру из списка фигур
     * @param figure фигура, которую нужно удалить
     */
    public void deleteFigure(Figure figure){
        this.figureList.remove(figure);
        if (figure.getTeam() == this){
            figure.unsetTeam();
        }
    }

    /**
     * Получить список фигур
     * @return список фигур
     */
    public List<Figure> getFigureList(){
        return this.figureList;
    }

    /**
     * Получить цвет команды
     * @return цвет
     */
    public Color getColor() {
        return color;
    }

    /**
     * Получить цвет команды в виде слова (White, Black)
     * @return цвет команды в виде строки
     */
    public String getColorAsString(){
        if (this.color == Color.WHITE){
            return "White";
        }
        else if(this.color == Color.BLACK){
            return "Black";
        }

        return "Undefined";
    }


    /**
     * Получить фигуру по ячейке
     * @param targetCell искомая ячейка
     */
    private Figure getFigureByCell(Cell targetCell){
        for(Figure figure: this.figureList){
            if (targetCell.getPosition().equals(figure.getCell().getPosition())){
                return figure;
            }
        }
        return null;
    }

    /**
     * Может ли команда атаковать вражеского короля
     * @param enemyKingCell ячейка вражеского короля
     * return может ли атаковать вражеского короля
     */
    public boolean canAttackEnemyKing(Cell enemyKingCell){
        // Для всех фигур команды
        for (Figure figure: figureList){
            // Для всех траекторий атаки фигуры
            for (Trajectory trajectory: figure.getAttackTrajectories()){
                // Можно ли попасть в ячейку короля
                if (trajectory.canGoToCell(enemyKingCell)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Есть ли фигура с доступными ходами
     * @return есть ли фигура с доступными ходами
     */
    public boolean hasFigureWithAvailableMoves(){
        // Для всех фигур
        for (Figure figure: getFigureList()){
            // Для всех траекторий фигуры
            for (Trajectory trajectory: figure.getMovementTrajectories()){
                // Есть ли доступные ходы
                if (!trajectory.getCells().isEmpty()){
                    return true;
                }
            }
            for (Trajectory trajectory: figure.getAttackTrajectories()){
                // Есть ли доступные ходы
                if (!trajectory.getCells().isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Получить ячейку короля
     * @return kingPosition ячейка короля
     */
    public Cell getKingCell(){

        // Для всех фигур команды
        for(Figure figure: figureList){
            // Возвращаем ячейку короля
            if (figure.getFigureType() == FiguresTypes.KING){
                return figure.getCell();
            }
        }
        return null;
    }

    /**
     * Переместить активную фигуру в заданную ячейку
     * @param targetCell целевая ячейка
     * @param figure фигура
     */
    public void moveFigure(Cell targetCell, Figure figure){
        if (figure != null && !figure.isFrozen()) {
            figure.moveTo(targetCell);
        }
    }

    /**
     * Построить траектории движения фигур
     */
    public void buildMovementTrajectories(){
        // Для всех фигур команды
        for(Figure figure: this.getFigureList()){
            // Для всех траекторий движения
            for(Trajectory trajectory: figure.getMovementTrajectories()){
                trajectory.buildTrajectory(figure.getCell());
            }
        }
    }

    /**
     * Построить траектории атаки фигур
     */
    public void buildAttackTrajectories(){
        // Для всех фигур команды
        for(Figure figure: this.getFigureList()){
            // Для всех траекторий движения
            for(Trajectory trajectory: figure.getAttackTrajectories()){
                trajectory.buildTrajectory(figure.getCell());
            }
        }
    }

    public Team cloneTeam(){
        return new Team(this.color);
    }

}

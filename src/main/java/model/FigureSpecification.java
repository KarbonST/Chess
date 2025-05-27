package model;

import model.Figures.FiguresTypes;

/**
 * Спецификация фигур
 */
public class FigureSpecification {

    /**
     * Тип фигуры
     */
    private final FiguresTypes figuresTypes;

    /**
     * Команда, которой фигура принадлежит
     */
    private final Team team;

    FigureSpecification(FiguresTypes figuresTypes, Team team){
        this.figuresTypes = figuresTypes;
        this.team = team;
    }

    /**
     * Получить команду
     * @return команда
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Получить тип фигуры
     * @return тип фигуры
     */
    public FiguresTypes getFiguresTypes() {
        return figuresTypes;
    }
}

package chess;

import chess.Figures.Figure;

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

    Team(Color color){
        this.figureList = new ArrayList<>();
        this.color = color;
    }
    /**
     * Добавить фигуру в команду
     */
    public void addFigure(Figure figure){
        figureList.add(figure);
    }

    /**
     * Получить список фигур
     */
    public List<Figure> getFigureList(){
        return this.figureList;
    }

}

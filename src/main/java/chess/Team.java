package chess;

import chess.Figures.Figure;
import chess.Figures.FiguresTypes;

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
     * Активная фигура
     */
    private Figure activeFigure;

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

    /**
     * Получить цвет команды
     */
    public Color getColor() {
        return color;
    }

    /**
     * Получить активную фигуру
     * @return активная фигура
     */
    public Figure getActiveFigure() {
        return activeFigure;
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
     */

    public void moveActiveFigure(Cell targetCell){

    }

}

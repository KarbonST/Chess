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
     * Получить позицию короля
     * @return kingPosition позиция короля
     */
    public CellPosition getKingPosition(){

        // Для всех фигур команды
        for(Figure figure: figureList){

            // Возвращаем позицию короля
            if (figure.getFigureType() == FiguresTypes.KING){
                return figure
            }
        }
    }

}

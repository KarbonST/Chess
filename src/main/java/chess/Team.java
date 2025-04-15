package chess;

import chess.Figures.Figure;
import java.util.List;
import java.awt.*;

/**
 * Команда.
 */
public class Team {

    /**
     * Цвет команды.
     */
    private Color teamColor;

    /**
     * Список фигур.
     */
    private List<Figure> figureList;

    Team(Color color){
        this.teamColor = color;
    }
}

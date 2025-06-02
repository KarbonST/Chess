package model.Figures;
import model.Team;

public class FiguresFactory {

    /**
     * Создать фигуру по типу и цвету.
     *
     * @param figureType тип фигуры, указанный в enum FigureTypes
     * @param team       команда, которой фигура будет принадлежать
     * @return объект фигуры нужного типа
     * @throws IllegalArgumentException если тип фигуры неизвестен
     */
    public static Figure createFigure(FiguresTypes figureType, Team team) {
        if (figureType == null) {
            throw new IllegalArgumentException("Тип фигуры не может быть null");
        }
        return switch (figureType) {
            case ROOK -> new Rook(team);
            case KNIGHT -> new Knight(team);
            case BISHOP -> new Bishop(team);
            case QUEEN -> new Queen(team);
            case KING -> new King(team);
            case PAWN -> new Pawn(team);
            case WIZARD -> new Wizard(team);
        };
    }
}

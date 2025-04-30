package chess;

import chess.Figures.Figure;

import java.awt.*;

public class GameSnapshotFactory {

    /**
     * Клонирование игровой ситуации в текущий момент времени
     * @return клонированная доска
     */
    public static GameSnapshot makeSnapshot(Board currentBoard){
        // Создаём экземпляр доски
        Board clonedBoard = new Board();

        // Создаём команды для клонированных фигур
        Team clonedWhiteTeam = new Team(Color.WHITE);
        Team clonedBlackTeam = new Team(Color.BLACK);

        // Для всех ячеек старой доски
        currentBoard.getCells().forEach((key, val) -> {
            // В ячейке имеется фигура
            if (val.getFigure() != null){
                Figure clonedFigure = val.getFigure().cloneFigure(); // Клонируем фигуру
                clonedBoard.getCellByPosition(key).setFigure(clonedFigure); // Добавляем клонированную фигуру в клонированную доску

                // Добавляем клонированную фигуру в команду
                if (val.getFigure().getTeam().getColor() == Color.WHITE){
                    clonedFigure.setTeam(clonedWhiteTeam);
                }
                else{
                    clonedFigure.setTeam(clonedBlackTeam);
                }
            }
        });

        // Строим траектории для всех фигур
        clonedWhiteTeam.buildMovementTrajectories();
        clonedWhiteTeam.buildAttackTrajectories();
        clonedBlackTeam.buildAttackTrajectories();
        clonedBlackTeam.buildMovementTrajectories();

        return new GameSnapshot(clonedBoard, clonedWhiteTeam, clonedBlackTeam);
    }
}

package chess;

import chess.Figures.Figure;
import chess.Trajectories.Trajectory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Судья.
 */
public class Judge {

    /**
     * Доска
     */
    private final Board board;

    Judge(Board board){
        this.board = board;
    }

    /**
     * Определить состояние игры
     * @param activeTeam активная команда
     * @param inactiveTeam неактивная команда
     * @return состояние игры
     */
    public GameStatus determineGameStatus(Team activeTeam, Team inactiveTeam){
        // Для всех фигур неактивной команды
        for (Figure figure: inactiveTeam.getFigureList()){
            // Перестроить траектории атаки
            for (Trajectory trajectory: figure.getAttackTrajectories()){
                trajectory.buildTrajectory(figure.getCell());
            }
        }

        // Для всех фигур активной команды
        for (Figure figure: activeTeam.getFigureList()){
            editTrajectories(activeTeam, figure, inactiveTeam);
        }

        // Может ли неактивная команда атаковать короля активной
        boolean canAttack = inactiveTeam.canAttackEnemyKing(activeTeam.getKingCell());

        // Есть ли у активной команды доступные ходы
        boolean hasAvailableMoves = activeTeam.hasFigureWithAvailableMoves();

        // У команды нет доступных ходов и её королю угрожают
        if (canAttack && !hasAvailableMoves){
            return GameStatus.CHECKMATE; // Объявлен мат
        }

        // У команды есть доступные ходы, но её королю угрожают
        if (canAttack){
            return GameStatus.CHECK; // Объявлен шах
        }

        // У команды нет доступных ходов и её королю не угрожают
        return GameStatus.STALEMATE; // Объявлен пат
    }

    /**
     * Редактировать траекторию
     * @param activeTeam активная команда
     * @param figureActiveTeam фигура активной команды
     * @param inactiveTeam неактивная команда
     */
    private void editTrajectories(Team activeTeam, Figure figureActiveTeam, Team inactiveTeam){
        // Объединить траектории в один лист
        List<Trajectory> trajectories = new ArrayList<>(figureActiveTeam.getMovementTrajectories());
        trajectories.addAll(figureActiveTeam.getAttackTrajectories());

        // Клон текущего состояния игры
        GameSnapshot gameSnapshot = GameSnapshotFactory.makeSnapshot(this.board);
        Board clonedBoard = gameSnapshot.board(); // Клонированная доска
        Team clonedWhiteTeam = gameSnapshot.whiteTeam(); // Клонированная команда белых
        Team clonedBlackTeam = gameSnapshot.blackTeam();  // Клонированная команда черных

        // Получаем клонированную фигуру
        Figure clonedFigure = clonedBoard.getCellByPosition(figureActiveTeam.getCell().getPosition()).getFigure();

        // Для всех траекторий
        for (Trajectory trajectory: trajectories){
            // Для всех ячеек траектории
            List<Cell> cellsToRemove = new ArrayList<>();
            for (Cell currentCell: trajectory.getCells()){
                // Перемещаем клонированную фигуру на ячейку клонированной доски
                clonedFigure.moveTo(clonedBoard.getCellByPosition(currentCell.getPosition()));
                boolean isAttacked;

                // Перестраиваем траектории атаки вражеской команды
                if (clonedFigure.getTeam().getColor() == Color.WHITE){
                    clonedWhiteTeam.buildAttackTrajectories();
                    isAttacked = clonedBlackTeam.canAttackEnemyKing(clonedWhiteTeam.getKingCell());
                }
                else{
                    clonedBlackTeam.buildAttackTrajectories();
                    isAttacked = clonedWhiteTeam.canAttackEnemyKing(clonedBlackTeam.getKingCell());
                }

                // Король находится под атакой
                if (isAttacked){
                    // Добавить ячейку в список удаляемых
                    cellsToRemove.add(currentCell);
                }


            }
            // Удалить ячейки из траектории
            trajectory.deleteCells(cellsToRemove);
        }

    }


}

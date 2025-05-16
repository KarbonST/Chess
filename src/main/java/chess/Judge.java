package chess;

import chess.Figures.Figure;
import chess.Figures.UndoableMove;
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

    public Judge(Board board){
        this.board = board;
    }

    /**
     * Определить состояние игры
     * @param activeTeam активная команда
     * @param inactiveTeam неактивная команда
     * @return состояние игры
     */
    public GameStatus determineGameStatus(Team activeTeam, Team inactiveTeam){
        // Для всех фигур активной команды
        for (Figure figure: activeTeam.getFigureList()){
            // Перестроить траектории атаки
            for (Trajectory trajectory: figure.getAttackTrajectories()){
                trajectory.buildTrajectory(figure.getCell());
            }
        }

        // Для всех фигур неактивной команды
        for (Figure figure: inactiveTeam.getFigureList()){
            // Перестроить траектории движения и атаки
            for (Trajectory trajectory: figure.getMovementTrajectories()){
                trajectory.buildTrajectory(figure.getCell());
            }
            for (Trajectory trajectory: figure.getAttackTrajectories()){
                trajectory.buildTrajectory(figure.getCell());
            }
            editTrajectories(figure, inactiveTeam);
        }

        // Может ли активная команда атаковать короля неактивной
        boolean canAttack = activeTeam.canAttackEnemyKing(inactiveTeam.getKingCell());

        // Есть ли у неактивной команды доступные ходы
        boolean hasAvailableMoves = inactiveTeam.hasFigureWithAvailableMoves();

        // У команды нет доступных ходов и её королю угрожают
        if (canAttack && !hasAvailableMoves){
            return GameStatus.CHECKMATE; // Объявлен мат
        }

        // У команды есть доступные ходы, но её королю угрожают
        if (canAttack){
            return GameStatus.CHECK; // Объявлен шах
        }

        // У команды нет доступных ходов и её королю не угрожают
        if (!hasAvailableMoves) {
            return GameStatus.STALEMATE; // Объявлен пат
        }

        return GameStatus.GAME_IS_ON;
    }

    /**
     * Редактировать траекторию
     * @param figureActiveTeam фигура активной команды
     * @param activeTeam активная команда
     */
    private void editTrajectories(Figure figureActiveTeam, Team activeTeam){
        // Объединить траектории в один лист
        List<Trajectory> trajectories = new ArrayList<>(figureActiveTeam.getMovementTrajectories());
        trajectories.addAll(figureActiveTeam.getAttackTrajectories());

        // Клон текущего состояния игры
        GameSnapshot gameSnapshot = GameSnapshotFactory.makeSnapshot(this.board);
        Board clonedBoard = gameSnapshot.board(); // Клонированная доска
        Team clonedActiveTeam;
        Team clonedInactiveTeam;
        // Клонируем активную и неактивную команду
        if (activeTeam.getColor() == Color.WHITE){
            clonedActiveTeam = gameSnapshot.whiteTeam();
            clonedInactiveTeam = gameSnapshot.blackTeam();
        }
        else{
            clonedActiveTeam = gameSnapshot.blackTeam();
            clonedInactiveTeam = gameSnapshot.whiteTeam();
        }

        // Получаем клонированную фигуру
        Figure clonedFigure = clonedBoard.getCellByPosition(figureActiveTeam.getCell().getPosition()).getFigure();

        // Для всех траекторий
        for (Trajectory trajectory: trajectories){
            // Для всех ячеек траектории
            List<Cell> cellsToRemove = new ArrayList<>();
            for (Cell currentCell: trajectory.getCells()){
                // Перемещаем клонированную фигуру на ячейку клонированной доски
                UndoableMove lastMove = clonedFigure.moveTo(clonedBoard.getCellByPosition(currentCell.getPosition()));
                boolean isAttacked;

                // Перестраиваем траектории атаки вражеской команды
                clonedInactiveTeam.buildAttackTrajectories();
                isAttacked = clonedInactiveTeam.canAttackEnemyKing(clonedActiveTeam.getKingCell());

                // Король находится под атакой
                if (isAttacked){
                    // Добавить ячейку в список удаляемых
                    cellsToRemove.add(currentCell);
                }

                // Возвращаем фигуру обратно
                lastMove.undo();
            }
            // Удалить ячейки из траектории
            trajectory.deleteCells(cellsToRemove);
        }

    }
}

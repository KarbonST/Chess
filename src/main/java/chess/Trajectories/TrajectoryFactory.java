package chess.Trajectories;

import chess.Direction;
import chess.Trajectories.AttackTrajectories.AttackTrajectory;
import chess.Trajectories.MovementTrajectories.MovementTrajectory;

import java.awt.*;
import java.util.List;

public interface TrajectoryFactory {

    /**
     * Создание траектории атаки
     * @param directions направления атаки
     * @param shiftPerStep смещение по строкам и колонкам за один шаг
     * @param stepsCount количество возможных шагов за ход
     * @param teamColor цвет команды
     * @return траектория атаки
     */

    public AttackTrajectory createAttackTrajectory(List<Direction> directions, int stepsCount, int[] shiftPerStep, Color teamColor);

    /**
     * Создание траектории движения
     * @param directions направления атаки
     * @param shiftPerStep смещение по строкам и колонкам за один шаг
     * @param stepsCount количество возможных шагов за ход
     * @return траектория движения
     */
    public MovementTrajectory createMovementTrajectory(List<Direction> directions, int stepsCount, int[] shiftPerStep);
}

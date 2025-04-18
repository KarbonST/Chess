package chess.Trajectories;

import chess.Board;
import chess.CellPosition;
import chess.Direction;
import chess.Figures.Figure;

import java.util.List;

/**
 * Траектория.
 */
public abstract class Trajectory {

    /**
     * Список позиций траектории
     */
    protected List<CellPosition> positions;

    /**
     * Доска
     */
    protected Board board;

    /**
     * Список направлений
     */
    protected final List<Direction> directions;

    /**
     * Количество возможных шагов
     */
    protected final int stepsCount;

    /**
     * Смещение за один шаг по строкам и колоннам
     */
    protected final int[] shiftPerStep;

    /**
     * @param board доска
     * @param directions направления
     * @param stepsCount количество возможных шагов
     * @param shiftPerStep смещение за один шаг по строкам и колоннам
     */
    public Trajectory(Board board, List<Direction> directions, int stepsCount, int[] shiftPerStep){
        this.board = board;
        this.directions = directions;
        this.stepsCount = stepsCount;
        this.shiftPerStep = shiftPerStep;
    }

    /**
     * Получить список позиций траектории
     */
    public List<CellPosition> getPositions(){
        return this.positions;
    }

    /**
     * Построить траекторию
     * @param startPosition стартовая позиция
     */
    public void buildTrajectory(CellPosition startPosition){

        // Для всех направлений
        for (Direction direction: this.directions){

            if (stepsCount > 0) {
                // Получить позицию в заданном направлении
                int rowAfterStep = direction.getDeltaRow() * shiftPerStep[0] + startPosition.getRow();
                int colAfterStep = direction.getDeltaCol() * shiftPerStep[1] + startPosition.getCol();

                // Заданная позиция не выходит за пределы доски
                if (Board.isBeyond(rowAfterStep, colAfterStep)) {
                    // Построить траекторию в заданном направлении
                    buildDirectionTrajectory(new CellPosition(rowAfterStep, colAfterStep), direction, this.stepsCount);
                }
            }
        }
    }

    /**
     * Построить траекторию в заданном направлении
     * @param pos текущая позиция
     * @param direction направление
     * @param steps количество доступных шагов
     */
    private void buildDirectionTrajectory(CellPosition pos, Direction direction, int steps){

        // Получить фигуру в ячейке
        Figure figure = board.getCellByPosition(pos).getFigure();

        // Уменьшаем количество доступных шагов
        int remindSteps = steps - 1;

        // Добавляем позицию ячейки в траекторию
        addCell(pos);

        // Если нет фигуры, либо можно перепрыгнуть её
        if (figure == null || this.shiftPerStep[0] > 1 || this.shiftPerStep[1] > 1) {
            // Получить позицию в заданном направлении
            int rowAfterStep = direction.getDeltaRow() * this.shiftPerStep[0] + pos.getRow();
            int colAfterStep = direction.getDeltaCol() * this.shiftPerStep[1] + pos.getCol();

            // Заданная позиция не выходит за пределы доски и остались ещё шаги
            if (Board.isBeyond(rowAfterStep, colAfterStep) && remindSteps > 0){
                // Строим траекторию в заданном направлении
                buildDirectionTrajectory(new CellPosition(rowAfterStep, colAfterStep), direction, remindSteps);
            }
        }
    }

    /**
     * Добавить ячейку в траекторию
     * @param cellPosition позиция ячейки
     */
    private void addCell(CellPosition cellPosition){
        this.positions.add(cellPosition);
    }

    /**
     * Удалить ячейку из траектории
     * @param cellPosition позиция ячейки
     */
    public void deleteCell(CellPosition cellPosition){
        this.positions.removeIf(pos -> pos == cellPosition);
    }
}

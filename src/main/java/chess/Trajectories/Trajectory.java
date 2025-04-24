package chess.Trajectories;

import chess.Board;
import chess.Cell;
import chess.CellPosition;
import chess.Direction;
import chess.Figures.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Траектория.
 */
public abstract class Trajectory {

    /**
     * Список ячеек в траектории
     */
    protected List<Cell> cells;

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
     * @param directions направления
     * @param stepsCount количество возможных шагов
     * @param shiftPerStep смещение за один шаг по строкам и колоннам
     */
    public Trajectory(List<Direction> directions, int stepsCount, int[] shiftPerStep){
        this.cells = new ArrayList<>();
        this.directions = directions;
        this.stepsCount = stepsCount;
        this.shiftPerStep = shiftPerStep;
    }

    /**
     * Получить список ячеек траектории
     */
    public List<Cell> getCells(){
        return this.cells;
    }

    /**
     * Можно ли попасть в заданную ячейку
     * @param targetCell целевая ячейка
     * @return можно ли переместиться
     */
    public boolean canGoToCell(Cell targetCell){

        // Для всех ячеек траектории
        for (Cell cell: this.cells){

            // Ячейка есть в списке
            if (cell.getPosition() == targetCell.getPosition()){
                return true;
            }
        }
        return false;
    }

    /**
     * Построить траекторию
     * @param startCell стартовая клетка
     */
    public void buildTrajectory(Cell startCell){
        //Очистка прошлой траектории
        this.cells.clear();

        // Для всех направлений
        for (Direction direction: this.directions){

            if (stepsCount > 0) {
                // Получить позицию в заданном направлении
                int rowAfterStep = direction.getDeltaRow() * shiftPerStep[0] + startCell.getPosition().getRow();
                int colAfterStep = direction.getDeltaCol() * shiftPerStep[1] + startCell.getPosition().getCol();
                CellPosition positionAfterStep = new CellPosition(rowAfterStep, colAfterStep);

                // Заданная позиция не выходит за пределы доски
                if (Board.isInsideTheBoard(rowAfterStep, colAfterStep)) {
                    //Получить соседа в заданном направлении
                    Cell neighbourCell = findCellAt(startCell, direction, positionAfterStep);
                    buildDirectionTrajectory(neighbourCell, direction, this.stepsCount);
                }
            }
        }
    }

    /**
     * Построить траекторию в заданном направлении
     * @param cell текущая клетка
     * @param direction направление
     * @param steps количество доступных шагов
     */
    private void buildDirectionTrajectory(Cell cell, Direction direction, int steps){

        // Получить фигуру в ячейке
        Figure figure = cell.getFigure();

        // Уменьшаем количество доступных шагов
        int remindSteps = steps - 1;

        // Добавляем ячейку в траекторию
        addCell(cell);

        // Если нет фигуры, либо можно перепрыгнуть её
        if (figure == null || this.shiftPerStep[0] > 1 || this.shiftPerStep[1] > 1) {
            // Получить позицию в заданном направлении
            int rowAfterStep = direction.getDeltaRow() * this.shiftPerStep[0] + cell.getPosition().getRow();
            int colAfterStep = direction.getDeltaCol() * this.shiftPerStep[1] + cell.getPosition().getCol();
            CellPosition positionAfterStep = new CellPosition(rowAfterStep, colAfterStep);

            // Заданная позиция не выходит за пределы доски и остались ещё шаги
            if (Board.isInsideTheBoard(rowAfterStep, colAfterStep) && remindSteps > 0){
                // Строим траекторию в заданном направлении
                Cell neighbourCell = findCellAt(cell, direction,positionAfterStep);
                buildDirectionTrajectory(neighbourCell, direction, remindSteps);
            }
        }
    }

    /**
     * Найти ячейку в заданном направлении
     * @param currentCell текущая ячейка
     * @param direction направление поиска
     * @param findPosition искомая позиция
     * @return ячейка с заданной позицией
     */
    private Cell findCellAt(Cell currentCell, Direction direction, CellPosition findPosition){
        if (currentCell == null){
            throw new IllegalArgumentException("Не удалось найти клетку с заданной позицией");
        }

        // Нашли искомую ячейку
        if (currentCell.getPosition().equals(findPosition)){
            return currentCell;
        }

        // Не нашли искомую ячейку - двигаемся дальше по направлению
        return findCellAt(currentCell.getNeighbour(direction), direction, findPosition);
    }

    /**
     * Добавить ячейку в траекторию
     * @param cell позиция ячейки
     */
    private void addCell(Cell cell){
        this.cells.add(cell);
    }

    /**
     * Удалить позиции ячеек из траектории
     * @param cellsToRemove список ячеек для удаления
     */
    public void deleteCells(List<Cell> cellsToRemove){
        this.cells.removeAll(cellsToRemove);
    }

}

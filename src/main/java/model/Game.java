package model;

import model.Figures.Figure;
import model.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Игра.
 */
public class Game {

    /**
     * Доска
     */
    private Board board;

    /**
     * Судья
     */
    private Judge judge;

    /**
     * Активная команда
     */
    private Team activeTeam;

    /**
     * Неактивная команда
     */
    private Team inactiveTeam;

    /**
     * Активная фигура
     */
    private Figure activeFigure;

    /**
     * Список команд
     */
    private final List<Team> teams;

    /**
     * Список слушателей изменений фигур
     */
    private final List<FigureActionListener> figureListeners;

    /**
     * Список слушателей результатов игры
     */
    private final List<GameStatusActionListener> gameStatusListeners;

    public Game(){
        this.teams = new ArrayList<>();
        this.figureListeners = new CopyOnWriteArrayList<>();
        this.gameStatusListeners = new CopyOnWriteArrayList<>();
    }

    /**
     * Начало игры
     */
    public void start(){

        // Создание доски, расстановки и судьи
        this.board = new Board();
        Placement placement = new Placement(this.board);
        this.judge = new Judge(this.board);

        // Инициализация команд
        Team white = placement.getWhiteTeam();
        Team black = placement.getBlackTeam();
        this.teams.clear();
        this.teams.add(white);
        this.teams.add(black);

        // Выбор того, кто ходит первым
        this.activeTeam = white;
        this.inactiveTeam = black;

        // Размораживаем фигуры
        unfreezeAll();

    }

    /**
     * Выбор активной фигуры
     * @param pos позиция фигуры
     */
    public void onFigureSelected(CellPosition pos){
        // Игнорировать, если выбрана фигура неактивной команды
        Cell figureCell = board.getCellByPosition(pos);
        Figure figure = figureCell.getFigure();
        if (figure == null || figure.getTeam() != activeTeam || figure.isFrozen()){
            clearSelection();
            return;
        }

        // Запоминаем выбранную фигуру
        this.activeFigure = figure;

        // Оповещаем GUI о выборе фигуры
        fireFigureActivated(activeFigure);
    }

    /**
     * Сделать ход активной фигуры
     * @param targetCellPos целевая позиция ячейки
     */
    public void onFigureMoved(CellPosition targetCellPos){
        // Не было активной фигуры
        if (this.activeFigure == null){
            return;
        }

        // Кликнули по фигуре своей команды
        if (board.getCellByPosition(targetCellPos).getFigure() != null) {
            if (board.getCellByPosition(targetCellPos).getFigure().getTeam() == this.activeTeam) {
                clearSelection();
                onFigureSelected(targetCellPos);
                return;
            }
        }

        // Кликнули по ячейке, которая не входит в траекторию фигуры
        Cell targetCell = board.getCellByPosition(targetCellPos);
        if (!this.activeFigure.getAllCellsFromTrajectories().contains(targetCell)){
            clearSelection();
            return;
        }

        // Двигаем фигуру
        Cell fromCell = this.activeFigure.getCell();
        this.activeTeam.moveFigure(targetCell, this.activeFigure);

        // Оповещаем GUI о перемещении фигуры
        fireFigureMoved(this.activeFigure, fromCell, targetCell);

        // Передать ход другой команде
        clearSelection();
        changeTeam();
    }

    /**
     * Заморозить все фигуры
     */
    private void freezeAll(){
        // Для каждой команды
        for (Team team: this.teams){
            // Для всех фигур команды
            for (Figure figure: team.getFigureList()){
                // Заморозить фигуру
                figure.freeze();
            }
        }
    }

    /**
     * Разморозить все фигуры
     */
    private void unfreezeAll(){
        // Для каждой команды
        for (Team team: this.teams){
            // Для всех фигур команды
            for (Figure figure: team.getFigureList()){
                // Разморозить фигуру
                figure.unfreeze();
            }
        }
    }

    /**
     * Деактивация фигуры
     */
    private void clearSelection(){
        if (this.activeFigure != null){
            fireFigureDeactivated(this.activeFigure);
        }

        this.activeFigure = null;
    }

    /**
     * Передать ход другой команде
     */
    private void changeTeam(){
        // Заморозка фигур
        freezeAll();

        // Определить состояние игры
        GameStatus gameStatus =  this.judge.determineGameStatus(this.activeTeam, this.inactiveTeam);

        // Был объявлен шах
        if (gameStatus == GameStatus.CHECK){
            fireCheck(this.inactiveTeam);
        }

        // Был объявлен пат
        if (gameStatus == GameStatus.STALEMATE){
            fireGameDrawn();
            return;
        }

        // Был объявлен мат
        if (gameStatus == GameStatus.CHECKMATE){
            fireGameWon(this.activeTeam);
            return;
        }

        // Передать ход другой команде
        int activeTeamIndex = this.teams.indexOf(this.activeTeam);
        this.inactiveTeam = this.activeTeam;
        this.activeTeam = this.teams.get((activeTeamIndex + 1) % this.teams.size());

        unfreezeAll();
    }

    /**
     * Регистрация слушателей изменения состояния фигур
     * @param l слушатель
     */
    public void addFigureActionListener(FigureActionListener l){
        figureListeners.add(l);
    }

    /**
     * Удаление слушателей изменения состояния фигур
     * @param l слушатель
     */
    public void removeFigureActionListener(FigureActionListener l){
        figureListeners.remove(l);
    }

    /**
     * Регистрация слушателей результата игры
     * @param l слушатель
     */
    public void addGameFinishActionListener(GameStatusActionListener l){
        gameStatusListeners.add(l);
    }

    /**
     * Удаление слушателей результата игры
     * @param l слушатель
     */
    public void removeGameFinishActionListener(GameStatusActionListener l){
        gameStatusListeners.remove(l);
    }

    /**
     * Рассылка событий активации фигуры
     * @param figure фигура
     */
    private void fireFigureActivated(Figure figure){
        FigureActivatedEvent event = new FigureActivatedEvent(this, figure);
        for (var l: this.figureListeners){
            l.figureActivated(event);
        }
    }

    /**
     * Рассылка событий деактивации фигуры
     * @param figure фигура
     */
    private void fireFigureDeactivated(Figure figure){
        FigureClearSelectedEvent event = new FigureClearSelectedEvent(this, figure);
        for (var l: this.figureListeners){
            l.figureDeactivated(event);
        }
    }

    /**
     * Рассылка событий перемещения фигуры
     * @param figure фигура
     * @param from ячейка, из которой фигура переместилась
     * @param to ячейка, в которую фигура переместилась
     */
    private void fireFigureMoved(Figure figure, Cell from, Cell to){
        FigureMovedEvent event = new FigureMovedEvent(this, figure, from, to);
        for (var l: this.figureListeners){
            l.figureMoved(event);
        }
    }

    /**
     * Рассылка событий завершения игры в ничью
     */
    private void fireGameDrawn(){
        GameStatusDrawEvent event = new GameStatusDrawEvent(this);
        for (var l: this.gameStatusListeners){
            l.gameFinishInDraw(event);
        }
    }

    /**
     * Рассылка событий завершения игры с победителем
     * @param winnerTeam команда победитель
     */
    private void fireGameWon(Team winnerTeam){
        GameStatusWinnerEvent event = new GameStatusWinnerEvent(this, winnerTeam);
        for (var l: this.gameStatusListeners){
            l.gameFinishWithWinner(event);
        }
    }

    /**
     * Рассылка событий постановки шаха команде
     * @param teamWithCheck команда, которой объявлен шах
     */
    private void fireCheck(Team teamWithCheck){
        GameStatusCheckEvent event = new GameStatusCheckEvent(this, teamWithCheck);
        for (var l: this.gameStatusListeners){
            l.teamHasCheck(event);
        }
    }

    /**
     * Получить доску
     * @return доска
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Работа с фигурой
     * @param cellPosition позиция ячейки
     */
    public void workWithFigure(CellPosition cellPosition){
        if (this.activeFigure == null){
            onFigureSelected(cellPosition);
        }
        else{
            onFigureMoved(cellPosition);
        }
    }

}

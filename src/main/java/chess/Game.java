package chess;

import chess.Figures.Figure;
import chess.events.*;

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
     * Расстановка
     */
    private Placement placement;

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
    private final List<GameFinishActionListener> gameFinishListeners;

    Game(){
        this.teams = new ArrayList<>();
        this.figureListeners = new CopyOnWriteArrayList<>();
        this.gameFinishListeners = new CopyOnWriteArrayList<>();
    }

    /**
     * Начало игры
     */
    public void start(){

        // Создание доски, расстановки и судьи
        this.board = new Board();
        this.placement = new Placement(this.board);
        this.judge = new Judge(this.board);

        // Инициализация команд
        Team white = this.placement.getWhiteTeam();
        Team black = this.placement.getBlackTeam();
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
        this.activeTeam.setActiveFigure(figure);

        // Оповещаем GUI о выборе фигуры
        fireFigureActivated(figure);
    }

    /**
     * Сделать ход активной фигуры
     * @param targetCellPos целевая позиция ячейки
     */
    public void onFigureMoved(CellPosition targetCellPos){
        // Не было активной фигуры
        if (this.activeTeam.getActiveFigure() == null){
            return;
        }

        // Кликнули по ячейке, которая не входит в траекторию фигуры
        Cell targetCell = board.getCellByPosition(targetCellPos);
        if (!this.activeTeam.getActiveFigure().getAllCellsFromTrajectories().contains(targetCell)){
            clearSelection();
            return;
        }

        // Двигаем фигуру
        Cell fromCell = this.activeTeam.getActiveFigure().getCell();
        this.activeTeam.moveActiveFigure(targetCell);

        // Оповещаем GUI о перемещении фигуры
        fireFigureMoved(this.activeTeam.getActiveFigure(), fromCell, targetCell);

        // Передать ход другой команде

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

    private void clearSelection(){
        activeTeam.setActiveFigure(null);

        // TODO оповестить GUI о том, что нужно убрать подсветку с активной фигуры
    }

    /**
     * Передать ход другой команде
     */
    private void changeTeam(){
        // Заморозка фигур
        freezeAll();

        // Передать ход другой команде
        int activeTeamIndex = this.teams.indexOf(this.activeTeam);
        this.inactiveTeam = this.activeTeam;
        this.activeTeam = this.teams.get((activeTeamIndex + 1) % this.teams.size());

        // Разморозить фигуры
        unfreezeAll();
    }


    /**
     * Определить исход игры
     * @return идет ли игра
     */
    private boolean determineGameFinish(){
        GameStatus gameStatus = judge.determineGameStatus(activeTeam, inactiveTeam);

        if (gameStatus == GameStatus.CHECKMATE){
            gameFinishWithWinner(inactiveTeam);
            return false;
        }

        if (gameStatus == GameStatus.STALEMATE){
            gameFinishInDraw();
            return false;
        }

        return true;
    }

    /**
     * Задать победителя
     */

    /**
     * Задать ничью
     */

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
    public void addGameFinishActionListener(GameFinishActionListener l){
        gameFinishListeners.add(l);
    }

    /**
     * Удаление слушателей результата игры
     * @param l слушатель
     */
    public void removeGameFinishActionListener(GameFinishActionListener l){
        gameFinishListeners.remove(l);
    }

    /**
     * Рассылка событий активации фигуры
     * @param figure фигура
     */
    private void fireFigureActivated(Figure figure){
        FigureActivatedEvent event = new FigureActivatedEvent(this, figure);
        for (var l: figureListeners){
            l.figureActivated(event);
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
        for (var l: figureListeners){
            l.figureMoved(event);
        }
    }

    /**
     * Рассылка событий завершения игры в ничью
     */
    private void gameFinishInDraw (){
        GameFinishDrawEvent event = new GameFinishDrawEvent(this);
        for (var l: gameFinishListeners){
            l.gameFinishInDraw(event);
        }
    }

    /**
     * Рассылка событий завершения игры с победителем
     */
    private void gameFinishWithWinner(Team winnerTeam){
        GameFinishWinnerEvent event = new GameFinishWinnerEvent(this, winnerTeam);
        for (var l: gameFinishListeners){
            l.gameFinishWithWinner(event);
        }
    }

}

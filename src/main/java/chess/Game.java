package chess;

import chess.Figures.Figure;
import chess.events.FigureActionListener;
import chess.events.FigureActivatedEvent;
import chess.events.FigureMovedEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Игра.
 */
public class Game {

    /**
     * Доска
     */
    private final Board board;

    /**
     * Расстановка
     */
    private final Placement placement;

    /**
     * Активная команда
     */
    private Team activeTeam;

    /**
     * Список команд
     */
    private final List<Team> teams;

    /**
     * Список слушателей
     */
    private final List<FigureActionListener> figureListeners = new CopyOnWriteArrayList<>();

    Game(){
        this.board = new Board();
        this.placement = new Placement(this.board);
        this.teams = List.of(this.placement.getWhiteTeam(), this.placement.getBlackTeam());
    }

    /**
     * Начало игры
     */
    public void start(){

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
     * Передать ход другой команде
     */

    /**
     * Заморозить фигуры
     */

    /**
     * Разморозить фигуры
     */

    /**
     * Определить исход игры
     */

    /**
     * Задать победителя
     */

    /**
     * Задать ничью
     */

    /**
     * Регистрация слушателей
     * @param l слушатель
     */
    public void addFigureActionListener(FigureActionListener l){
        figureListeners.add(l);
    }

    /**
     * Удаление слушателей
     * @param l слушатель
     */
    public void removeFigureActionListener(FigureActionListener l){
        figureListeners.remove(l);
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

}

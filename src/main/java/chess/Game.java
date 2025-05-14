package chess;

import chess.Figures.Figure;
import chess.events.FigureActionListener;
import chess.events.FigureActivatedEvent;
import chess.events.FigureMovedEvent;

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
     * Список слушателей
     */
    private final List<FigureActionListener> figureListeners = new CopyOnWriteArrayList<>();

    Game(){
        this.teams = new ArrayList<>();
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
    private void changeTeam(){
        // Заморозка фигур
        freezeAll();

        // Передать ход другой команде
        int activeTeamIndex = this.teams.indexOf(this.activeTeam);
        this.inactiveTeam = this.activeTeam;
        this.activeTeam = this.teams.get((activeTeamIndex + 1) % this.teams.size());

        // Определить исход игры

    }


    /**
     * Определить исход игры
     */
    private void determineGameFinish(){
        GameStatus gameStatus = judge.determineGameStatus(activeTeam, inactiveTeam);
    }

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

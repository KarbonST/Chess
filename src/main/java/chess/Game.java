package chess;

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
     * Начало игры
     */
    public void startGame(){

        // Инициализация
        this.board = new Board();
        this.placement = new Placement(board);

    }

}

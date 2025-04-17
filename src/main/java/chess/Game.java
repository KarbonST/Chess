package chess;

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
     * Команда белых
     */
    private final Team whiteTeam;

    /**
     * Команда черных
     */
    private final Team blackTeam;

    Game(){
        this.board = new Board();
        this.placement = new Placement(this.board);
        this.whiteTeam = this.placement.getWhiteTeam();
        this.blackTeam = this.placement.getBlackTeam();
    }
}

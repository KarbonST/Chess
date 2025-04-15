package chess;

import java.awt.*;
import java.util.List;

/**
 * Расстановка фигур на доске.
 */
public class Placement {
    /**
     * Доска.
     */
    private Board board;

    /**
     * Команды.
     */
    private List<Team> teamList;

    Placement(Board board){
        this.board = board;
    }

    /**
     * Заселение доски.
     */
    public void populateBoard(){
        // Создание команд
        createTeams();
    }

    /**
     * Создание команд.
     */
    private void createTeams(){
        Team whiteTeam = new Team(Color.WHITE);
        Team blackTeam = new Team(Color.BLACK);

        this.teamList.add(whiteTeam);
        this.teamList.add(blackTeam);
    }
}

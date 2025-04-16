package chess.Figures;

import chess.Team;

/**
 * Ферзь.
 */
public class Queen extends Figure{

    Queen(Team team) {
        super(team);
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.QUEEN;
    }
}

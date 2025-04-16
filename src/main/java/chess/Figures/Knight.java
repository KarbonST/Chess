package chess.Figures;

import chess.Team;

/**
 * Конь.
 */
public class Knight extends Figure{

    Knight(Team team) {
        super(team);
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.KNIGHT;
    }
}

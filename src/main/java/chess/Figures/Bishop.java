package chess.Figures;

import chess.Team;

/**
 * Слон.
 */
public class Bishop extends Figure{

    Bishop(Team team) {
        super(team);
    }
    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.BISHOP;
    }
}

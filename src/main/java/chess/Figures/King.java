package chess.Figures;

import chess.Team;

/**
 * Король.
 */
public class King extends Figure{

    King(Team team) {
        super(team);
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.KING;
    }
}

package chess.Figures;

import chess.Team;

/**
 * Пешка.
 */
public class Pawn extends Figure{

    Pawn(Team team) {
        super(team);
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.PAWN;
    }
}

package chess.Figures;

import chess.Team;

import java.awt.*;

/**
 * Ладья.
 */
public class Rook extends Figure{

    Rook(Team team) {
        super(team);
    }

    @Override
    public FiguresTypes getFigureType() {
        return FiguresTypes.ROOK;
    }
}

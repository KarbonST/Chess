package ui.events;

import model.CellPosition;

import java.util.EventListener;

public interface BoardClickListener extends EventListener {
    void cellClicked(CellPosition pos);
}

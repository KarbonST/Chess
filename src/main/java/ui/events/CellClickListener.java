package ui.events;

import model.CellPosition;

import java.util.EventListener;

public interface CellClickListener extends EventListener {
    void cellClicked(CellClickEvent cellClickEvent);
}

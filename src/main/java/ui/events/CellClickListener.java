package ui.events;

import java.util.EventListener;

public interface CellClickListener extends EventListener {
    void cellClicked(CellClickEvent cellClickEvent);
}

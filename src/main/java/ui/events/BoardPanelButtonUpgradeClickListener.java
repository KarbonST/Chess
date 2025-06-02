package ui.events;

import model.CellPosition;

import java.util.EventListener;

public interface BoardPanelButtonUpgradeClickListener extends EventListener {
    void buttonUpgradeClicked(CellPosition pos);
}

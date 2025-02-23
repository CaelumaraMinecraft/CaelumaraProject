package top.mckingdom.powerfulterritory.configs;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.gui.GUIPathContainer;

public enum PowerfulTerritoryGUI implements GUIPathContainer {
    LAND$CATEGORY_EDITOR,


    ;
    private final @NotNull String path;

    PowerfulTerritoryGUI() {
        this.path = "powerful-territory/" + GUIPathContainer.translateEnumPath(this);
    }

    @Override
    public @NotNull String getGUIPath() {
        return this.path;
    }
}

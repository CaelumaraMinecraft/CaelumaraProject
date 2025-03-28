package top.mckingdom.auspice.configs;

import org.kingdoms.gui.GUIPathContainer;

public enum AuspiceGUI implements GUIPathContainer {
  LAND$CATEGORY_EDITOR,


  ;
  private final String path;

  AuspiceGUI() {
    this.path = "auspice-addon/" + GUIPathContainer.translateEnumPath(this);
  }

  @Override
  public String getGUIPath() {
    return this.path;
  }
}

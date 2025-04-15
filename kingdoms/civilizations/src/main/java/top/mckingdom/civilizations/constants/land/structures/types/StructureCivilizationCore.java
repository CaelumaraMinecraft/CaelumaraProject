package top.mckingdom.civilizations.constants.land.structures.types;

import org.kingdoms.constants.land.abstraction.gui.KingdomItemGUIContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureType;
import org.kingdoms.gui.InteractiveGUI;

public class StructureCivilizationCore extends StructureType {

  public StructureCivilizationCore() {
    super("civilization-core");
  }

  @Override
  public InteractiveGUI open(KingdomItemGUIContext<Structure> context) {
    return null;
  }

}

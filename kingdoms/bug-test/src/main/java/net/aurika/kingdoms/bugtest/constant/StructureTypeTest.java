package net.aurika.kingdoms.bugtest.constant;

import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.abstraction.gui.KingdomItemGUIContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureType;
import org.kingdoms.gui.InteractiveGUI;

public class StructureTypeTest extends StructureType {

  public StructureTypeTest() {
    super("test");
  }

  @Override
  public @Nullable InteractiveGUI open(KingdomItemGUIContext<Structure> kingdomItemGUIContext) {
    return null;
  }

}

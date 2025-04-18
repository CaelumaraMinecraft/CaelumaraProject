package top.mckingdom.civilizations.constants.land.structures.types;

import org.kingdoms.constants.land.abstraction.data.KingdomItemBuilder;
import org.kingdoms.constants.land.abstraction.gui.KingdomItemGUIContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.constants.land.structures.StructureType;
import org.kingdoms.gui.InteractiveGUI;

public class StructureCivilizationNode extends StructureType {

  public StructureCivilizationNode() {
    super("civilization-node");
  }

  @Override
  public InteractiveGUI open(KingdomItemGUIContext<Structure> context) {
    return null;
  }

  @Override
  public Structure build(KingdomItemBuilder<Structure, StructureStyle, StructureType> kingdomItemBuilder) {
    return super.build(kingdomItemBuilder);
  }

}

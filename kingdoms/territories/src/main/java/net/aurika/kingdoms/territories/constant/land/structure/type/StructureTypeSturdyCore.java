package net.aurika.kingdoms.territories.constant.land.structure.type;

import net.aurika.kingdoms.territories.TerritoriesAddon;
import net.aurika.kingdoms.territories.constant.land.structure.object.SturdyCore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.abstraction.data.KingdomItemBuilder;
import org.kingdoms.constants.land.abstraction.gui.KingdomItemGUIContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.constants.land.structures.StructureType;
import org.kingdoms.gui.InteractiveGUI;

public class StructureTypeSturdyCore extends StructureType {

  public static final StructureTypeSturdyCore INSTANCE = new StructureTypeSturdyCore();

  protected StructureTypeSturdyCore() {
    super(TerritoriesAddon.NAMESPACE + "_sturdy-core");
  }

  @Override
  public @Nullable InteractiveGUI open(@Nullable KingdomItemGUIContext<Structure> kingdomItemGUIContext) {
    return null;
  }

  @Override
  public @NotNull SturdyCore build(@NotNull KingdomItemBuilder<Structure, StructureStyle, StructureType> kingdomItemBuilder) {
    return new SturdyCore(kingdomItemBuilder.getStyle(), kingdomItemBuilder.getLocation());
  }

}

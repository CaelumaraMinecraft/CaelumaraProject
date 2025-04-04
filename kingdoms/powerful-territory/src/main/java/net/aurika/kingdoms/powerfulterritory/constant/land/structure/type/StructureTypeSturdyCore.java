package net.aurika.kingdoms.powerfulterritory.constant.land.structure.type;

import net.aurika.kingdoms.powerfulterritory.PowerfulTerritoryAddon;
import net.aurika.kingdoms.powerfulterritory.constant.land.structure.SturdyCore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.abstraction.data.KingdomItemBuilder;
import org.kingdoms.constants.land.abstraction.gui.KingdomItemGUIContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.constants.land.structures.StructureType;
import org.kingdoms.gui.InteractiveGUI;

public class StructureTypeSturdyCore extends StructureType {

  public StructureTypeSturdyCore(@NotNull String name) {
    super(PowerfulTerritoryAddon.KEY + "-sturdy-core");
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

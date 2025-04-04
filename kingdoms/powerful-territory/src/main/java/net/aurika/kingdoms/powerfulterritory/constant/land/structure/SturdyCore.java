package net.aurika.kingdoms.powerfulterritory.constant.land.structure;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.location.SimpleLocation;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;

public class SturdyCore extends Structure {

  public SturdyCore(@NotNull String world, int x, int y, int z, @NotNull StructureStyle style) {
    super(world, x, y, z, style);
  }

  public SturdyCore(@NotNull StructureStyle style, @NotNull SimpleLocation location) {
    super(style, location);
  }

}

package net.aurika.kingdoms.territories.constant.land.structure.object;

import net.aurika.common.validate.Validate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleLocation;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;

public class SturdyCore extends Structure {

  public static boolean isProtectedBySturdyCore(@NotNull Land land) {
    Validate.Arg.notNull(land, "land");
    return land.getStructure(SturdyCore.class) != null;
  }

  public SturdyCore(@NotNull String world, int x, int y, int z, @NotNull StructureStyle style) {
    super(world, x, y, z, style);
  }

  public SturdyCore(@NonNull StructureStyle style, @NonNull SimpleLocation location) {
    super(style, location);
  }

}

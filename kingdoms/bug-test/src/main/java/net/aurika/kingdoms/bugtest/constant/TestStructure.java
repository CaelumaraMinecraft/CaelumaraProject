package net.aurika.kingdoms.bugtest.constant;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.location.SimpleLocation;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;

public class TestStructure extends Structure {

  public TestStructure(@NotNull String world, int x, int y, int z, @NotNull StructureStyle style) {
    super(world, x, y, z, style);
  }

  public TestStructure(@NonNull StructureStyle style, @NonNull SimpleLocation location) {
    super(style, location);
  }

}

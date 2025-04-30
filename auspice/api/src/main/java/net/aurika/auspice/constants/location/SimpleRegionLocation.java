package net.aurika.auspice.constants.location;

import net.aurika.common.examination.ExaminablePropertyGetter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SimpleRegionLocation extends AbstractSimple2Location {

  @Contract("_, _, _ -> new")
  public static @NotNull SimpleRegionLocation simpleRegionLocation(@NotNull String worldName, int x, int z) {
    return new SimpleRegionLocation(worldName, x, z);
  }

  private SimpleRegionLocation(@NotNull String worldName, int x, int z) {
    super(worldName, x, z);
  }

  @Override
  @ExaminablePropertyGetter(VAL_WORLD$NAME)
  public @NotNull String worldName() {
    return super.worldName();
  }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  public int gridX() {
    return super.gridX();
  }

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  public int gridZ() {
    return super.gridZ();
  }

  @Override
  public @NotNull SimpleRegionLocation clone() {
    return (SimpleRegionLocation) super.clone();
  }

}

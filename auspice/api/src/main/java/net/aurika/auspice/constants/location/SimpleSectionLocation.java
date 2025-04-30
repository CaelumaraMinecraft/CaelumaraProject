package net.aurika.auspice.constants.location;

import net.aurika.common.examination.ExaminablePropertyGetter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SimpleSectionLocation extends AbstractSimple3Location {

  @Contract("_, _, _, _ -> new")
  public static @NotNull SimpleSectionLocation simpleSectionLocation(@NotNull String worldName, int x, int y, int z) {
    return new SimpleSectionLocation(worldName, x, y, z);
  }

  private SimpleSectionLocation(@NotNull String worldName, int x, int y, int z) {
    super(worldName, x, y, z);
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
  @ExaminablePropertyGetter(VAL_Y)
  public int gridY() {
    return super.gridY();
  }

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  public int gridZ() {
    return super.gridZ();
  }

  @Override
  public @NotNull SimpleSectionLocation clone() {
    return (SimpleSectionLocation) super.clone();
  }

}

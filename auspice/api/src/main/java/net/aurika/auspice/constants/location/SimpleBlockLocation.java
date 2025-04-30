package net.aurika.auspice.constants.location;

import net.aurika.common.annotation.data.Immutable;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.kyori.examination.Examinable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Immutable
public final class SimpleBlockLocation extends AbstractSimple3Location implements Examinable {

  @Contract("_, _, _, _ -> new")
  public static @NotNull SimpleBlockLocation simpleBlockLocation(@NotNull String worldName, int x, int y, int z) {
    return new SimpleBlockLocation(worldName, x, y, z);
  }

  private SimpleBlockLocation(@NotNull String worldName, int x, int y, int z) {
    super(worldName, x, y, z);
  }

  @Contract(value = "-> new", pure = true)
  public @NotNull SimpleChunkLocation getChunkLocation() {
    return SimpleChunkLocation.simpleChunkLocation(worldName, this.x >> 4, this.z >> 4);
  }

  @Contract(value = "_ -> new", pure = true)
  public @NotNull SimpleBlockLocation add(@NotNull SimpleBlockLocation blockLocation) {
    return new SimpleBlockLocation(
        worldName, x + blockLocation.gridX(), y + blockLocation.gridY(), z + blockLocation.gridZ());
  }

  @Contract(value = "_ -> new", pure = true)
  public @NotNull SimpleBlockLocation subtract(@NotNull SimpleBlockLocation blockLocation) {
    return new SimpleBlockLocation(
        worldName, x - blockLocation.gridX(), y - blockLocation.gridY(), z - blockLocation.gridZ());
  }

  @Contract(value = "_ -> new", pure = true)
  public @NotNull SimpleBlockLocation multiply(@NotNull SimpleBlockLocation blockLocation) {
    return new SimpleBlockLocation(
        worldName, x * blockLocation.gridX(), y * blockLocation.gridY(), z * blockLocation.gridZ());
  }

  @Contract(value = "_ -> new", pure = true)
  public @NotNull SimpleBlockLocation divide(@NotNull SimpleBlockLocation blockLocation) {
    return new SimpleBlockLocation(
        worldName, x / blockLocation.gridX(), y / blockLocation.gridY(), z / blockLocation.gridZ());
  }

  @Override
  @ExaminablePropertyGetter(VAL_WORLD$NAME)
  public @NotNull String worldName() { return super.worldName(); }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  public int gridX() { return super.gridX(); }

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  public int gridY() { return super.gridY(); }

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  public int gridZ() { return super.gridZ(); }

  @Override
  public @NotNull SimpleBlockLocation clone() {
    return (SimpleBlockLocation) super.clone();
  }

}

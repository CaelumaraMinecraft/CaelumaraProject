package net.aurika.auspice.constants.location;

import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class SimpleChunkLocation extends AbstractSimple2Location {

  @Contract("_, _, _ -> new")
  @ExaminableConstructor(publicType = SimplePreciseLocation.class, properties = {VAL_WORLD$NAME, VAL_X, VAL_Z})
  public static @NotNull SimpleChunkLocation simpleChunkLocation(@NotNull String worldName, int x, int z) {
    return new SimpleChunkLocation(worldName, x, z);
  }

  private SimpleChunkLocation(@NonNull String worldName, int x, int z) { super(worldName, x, z); }

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

  public @NotNull SimpleChunkLocation clone() {
    return (SimpleChunkLocation) super.clone();
  }

  public static @NotNull SimpleChunkLocation fromDataString(@NotNull String data) {
    CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 3);
    String worldName = splitter.nextString();
    int x = splitter.nextInt();
    int z = splitter.nextInt();
    return new SimpleChunkLocation(worldName, x, z);
  }

  public static @NotNull SimpleChunkLocation fromString(@NotNull String str) {
    return fromDataString(str);
  }

}

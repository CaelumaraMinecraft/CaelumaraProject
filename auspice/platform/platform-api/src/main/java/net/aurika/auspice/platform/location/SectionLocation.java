package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.location.grid.Grid3Location;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.LocationProperties.*;

public interface SectionLocation extends Grid3Location, SectionLocationLike, Examinable {

  @Contract("_ -> new")
  static @NotNull SectionLocation sectionLocation(@NotNull SectionLocationLike section) {
    Validate.Arg.notNull(section, "section");
    return sectionLocation(section.world(), section.sectionX(), section.sectionY(), section.sectionZ());
  }

  @Contract("_, _, _, _ -> new")
  @ExaminableConstructor(publicType = SectionLocation.class, properties = {VAL_WORLD, VAL_X, VAL_Y, VAL_Z})
  static @NotNull SectionLocation sectionLocation(@NotNull World world, int x, int y, int z) {
    return new SectionLocationImpl(world, x, y, z);
  }

  @Override
  @ExaminablePropertyGetter(VAL_WORLD)
  @NotNull World world();

  @Override
  default int gridX() { return sectionX(); }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  int sectionX();

  @Override
  default int gridY() { return sectionY(); }

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  int sectionY();

  @Override
  default int gridZ() { return sectionZ(); }

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  int sectionZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Grid3Location.super.examinableProperties();
  }

}

final class SectionLocationImpl implements SectionLocation {

  private final @NotNull World world;
  private final int x;
  private final int y;
  private final int z;

  SectionLocationImpl(@NotNull World world, int x, int y, int z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull World world() { return world; }

  @Override
  public int sectionX() { return x; }

  @Override
  public int sectionY() { return y; }

  @Override
  public int sectionZ() { return z; }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SectionLocation)) return false;
    SectionLocation that = (SectionLocation) obj;
    return x == that.sectionX() && y == that.sectionY() && z == that.sectionZ() && Objects.equals(world, that.world());
  }

  @Override
  public int hashCode() {
    return Objects.hash(world, x, y, z);
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}

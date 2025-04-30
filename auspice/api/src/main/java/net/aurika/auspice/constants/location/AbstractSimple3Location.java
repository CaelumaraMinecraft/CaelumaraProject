package net.aurika.auspice.constants.location;

import net.aurika.auspice.platform.location.grid.Grid3Pos;
import net.aurika.auspice.platform.world.WorldNameAware;
import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

abstract class AbstractSimple3Location implements WorldNameAware, Grid3Pos, DataStringRepresentation, Examinable, Cloneable {

  protected final @NotNull String worldName;
  protected final int x;
  protected final int y;
  protected final int z;

  AbstractSimple3Location(@NotNull String worldName, int x, int y, int z) {
    Validate.Arg.notEmpty(worldName, "worldName");
    this.worldName = worldName;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull String worldName() { return this.worldName; }

  @Override
  public int gridX() { return this.x; }

  @Override
  public int gridY() { return this.y; }

  @Override
  public int gridZ() { return this.z; }

  @Override
  public final @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_WORLD$NAME, worldName), // worldName
        ExaminableProperty.of(VAL_X, x),                  // x
        ExaminableProperty.of(VAL_Y, y),                  // y
        ExaminableProperty.of(VAL_Z, z)                   // z
    );
  }

  @Override
  public @NotNull String asDataString() {
    return CommaDataSplitStrategy.toString(new Object[]{worldName, x, y, z});
  }

  @Override
  public final int hashCode() {
    return Objects.hash(worldName, x, y, z);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AbstractSimple3Location that = (AbstractSimple3Location) o;
    return x == that.x && y == that.y && z == that.z && Objects.equals(worldName, that.worldName);
  }

  @Override
  public @NotNull AbstractSimple3Location clone() {
    try {
      return (AbstractSimple3Location) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

  public final @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}

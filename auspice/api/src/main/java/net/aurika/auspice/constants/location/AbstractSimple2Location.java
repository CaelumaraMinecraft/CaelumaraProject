package net.aurika.auspice.constants.location;

import net.aurika.auspice.platform.location.grid.Grid2Pos;
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

class AbstractSimple2Location implements WorldNameAware, Grid2Pos, DataStringRepresentation, Examinable, Cloneable {

  protected final @NotNull String worldName;
  protected final int x;
  protected final int z;

  AbstractSimple2Location(@NotNull String worldName, int x, int z) {
    Validate.Arg.notEmpty(worldName, "worldName");
    this.worldName = worldName;
    this.x = x;
    this.z = z;
  }

  @Override
  public @NotNull String worldName() { return this.worldName; }

  @Override
  public int gridX() { return x; }

  @Override
  public int gridZ() { return z; }

  @Override
  public final @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_WORLD$NAME, worldName), // worldName
        ExaminableProperty.of(VAL_X, x),                  // x
        ExaminableProperty.of(VAL_Z, z)                   // z
    );
  }

  @Override
  public @NotNull String asDataString() {
    return CommaDataSplitStrategy.toString(new Object[]{worldName, x, z});
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) return false;
    AbstractSimple2Location that = (AbstractSimple2Location) obj;
    return x == that.x && z == that.z && Objects.equals(worldName, that.worldName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(worldName, x, z);
  }

  @Override
  public @NotNull AbstractSimple2Location clone() {
    try {
      return (AbstractSimple2Location) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}

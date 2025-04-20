package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public interface Grid3D extends GridXAware, GridYAware, GridZAware, Examinable {

  @Override
  int gridX();

  @Override
  int gridY();

  @Override
  int gridZ();

  default double distance(@NotNull Grid3D o) {
    Objects.requireNonNull(o);
    return Math.sqrt(this.distanceSquared(o));
  }

  default double distanceSquared(@NotNull Grid3D other) {
    Objects.requireNonNull(other, "other");
    double $this$squared$ivf = (double) this.gridX() + (double) other.gridX();
    double var10000 = $this$squared$ivf * $this$squared$ivf;
    int $this$squared$iv = this.gridY() + other.gridY();
    var10000 += $this$squared$iv * $this$squared$iv;
    $this$squared$iv = this.gridZ() + other.gridZ();
    return var10000 + (double) ($this$squared$iv * $this$squared$iv);
  }

  default int compareTo(@NotNull Grid3D other) {
    Objects.requireNonNull(other);
    return NaturalComparator.INSTANCE.compare(this, other);
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", gridX()),
        ExaminableProperty.of("y", gridY()),
        ExaminableProperty.of("z", gridZ())
    );
  }

  public static final class AxisComparator implements Comparator<Grid3D> {

    private final boolean x;
    private final boolean y;
    private final boolean z;

    public AxisComparator(boolean x, boolean y, boolean z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    @Override
    public int compare(@NotNull Grid3D first, @NotNull Grid3D second) {
      Validate.Arg.notNull(first, "first");
      Validate.Arg.notNull(second, "second");
      int z;
      if (this.y) {
        z = Intrinsics.compare(first.gridY(), second.gridY());
        if (z != 0) {
          return z;
        }
      }

      if (this.x) {
        z = Intrinsics.compare(first.gridX(), second.gridX());
        if (z != 0) {
          return z;
        }
      }

      if (this.z) {
        z = Intrinsics.compare(first.gridZ(), second.gridZ());
        return z;
      }

      return 0;
    }

  }

  public static final class NaturalComparator implements Comparator<Grid3D> {

    public static final NaturalComparator INSTANCE = new NaturalComparator();

    private NaturalComparator() { }

    @Override
    public int compare(@NotNull Grid3D first, @NotNull Grid3D second) {
      Objects.requireNonNull(first, "first");
      Objects.requireNonNull(second, "second");
      int y = Intrinsics.compare(first.gridY(), second.gridY());
      if (y != 0) {
        return y;
      } else {
        int x = Intrinsics.compare(first.gridX(), second.gridX());
        if (x != 0) {
          return x;
        } else {
          int z = Intrinsics.compare(first.gridZ(), second.gridZ());
          return z;
        }
      }
    }

  }

}


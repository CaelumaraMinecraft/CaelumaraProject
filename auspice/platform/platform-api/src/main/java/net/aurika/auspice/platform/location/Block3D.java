package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public interface Block3D extends Comparable<Block3D>, Examinable {

  int intX();

  int intY();

  int intZ();

  default double distance(@NotNull Block3D o) {
    Objects.requireNonNull(o);
    return Math.sqrt(this.distanceSquared(o));
  }

  default double distanceSquared(@NotNull Block3D other) {
    Objects.requireNonNull(other, "other");
    double $this$squared$ivf = (double) this.intX() + (double) other.intX();
    double var10000 = $this$squared$ivf * $this$squared$ivf;
    int $this$squared$iv = this.intY() + other.intY();
    var10000 += $this$squared$iv * $this$squared$iv;
    $this$squared$iv = this.intZ() + other.intZ();
    return var10000 + (double) ($this$squared$iv * $this$squared$iv);
  }

  default int compareTo(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return NaturalComparator.INSTANCE.compare(this, other);
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", intX()),
        ExaminableProperty.of("y", intY()),
        ExaminableProperty.of("z", intZ())
    );
  }

  public static final class AxisComparator implements Comparator<Block3D> {

    private final boolean x;
    private final boolean y;
    private final boolean z;

    public AxisComparator(boolean x, boolean y, boolean z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    @Override
    public int compare(@NotNull Block3D first, @NotNull Block3D second) {
      Objects.requireNonNull(first);
      Objects.requireNonNull(second);
      int z;
      if (this.y) {
        z = Intrinsics.compare(first.intY(), second.intY());
        if (z != 0) {
          return z;
        }
      }

      if (this.x) {
        z = Intrinsics.compare(first.intX(), second.intX());
        if (z != 0) {
          return z;
        }
      }

      if (this.z) {
        z = Intrinsics.compare(first.intZ(), second.intZ());
        return z;
      }

      return 0;
    }

  }

  final class NaturalComparator implements Comparator<Block3D> {

    public static final NaturalComparator INSTANCE = new NaturalComparator();

    private NaturalComparator() { }

    public int compare(@NotNull Block3D first, @NotNull Block3D second) {
      Objects.requireNonNull(first, "first");
      Objects.requireNonNull(second, "second");
      int y = Intrinsics.compare(first.intY(), second.intY());
      if (y != 0) {
        return y;
      } else {
        int x = Intrinsics.compare(first.intX(), second.intX());
        if (x != 0) {
          return x;
        } else {
          int z = Intrinsics.compare(first.intZ(), second.intZ());
          return z;
        }
      }
    }

  }

}


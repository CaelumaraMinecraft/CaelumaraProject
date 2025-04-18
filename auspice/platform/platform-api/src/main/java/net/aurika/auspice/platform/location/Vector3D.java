package net.aurika.auspice.platform.location;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public interface Vector3D extends Comparable<Vector3D>, VectorXAware, VectorYAware, VectorZAware {

  @Override
  double x();

  @Override
  double y();

  @Override
  double z();

  @Override
  default int compareTo(@NotNull Vector3D other) {
    Validate.Arg.notNull(other, "other");
    return NaturalComparator.INSTANCE.compare(this, other);
  }

  final class NaturalComparator implements Comparator<Vector3D> {

    public static final NaturalComparator INSTANCE = new NaturalComparator();

    private NaturalComparator() { }

    @Override
    public int compare(@NotNull Vector3D first, @NotNull Vector3D second) {
      Validate.Arg.notNull(first, "first");
      Validate.Arg.notNull(second, "second");
      int y = Double.compare(first.y(), second.y());
      if (y != 0) {
        return y;
      } else {
        int x = Double.compare(first.x(), second.x());
        if (x != 0) {
          return x;
        } else {
          int z = Double.compare(first.z(), second.z());
          return z;
        }
      }
    }

  }

}

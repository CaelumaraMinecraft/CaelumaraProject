package net.aurika.auspice.platform.location;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public interface Float3D extends Comparable<Float3D>, FloatXAware, FloatYAware, FloatZAware {

  @Override
  double floatX();

  @Override
  double floatY();

  @Override
  double floatZ();

  @Override
  default int compareTo(@NotNull Float3D other) {
    Validate.Arg.notNull(other, "other");
    return NaturalComparator.INSTANCE.compare(this, other);
  }

  public static final class NaturalComparator implements Comparator<Float3D> {

    public static final NaturalComparator INSTANCE = new NaturalComparator();

    private NaturalComparator() { }

    @Override
    public int compare(@NotNull Float3D first, @NotNull Float3D second) {
      Validate.Arg.notNull(first, "first");
      Validate.Arg.notNull(second, "second");
      int y = Double.compare(first.floatY(), second.floatY());
      if (y != 0) {
        return y;
      } else {
        int x = Double.compare(first.floatX(), second.floatX());
        if (x != 0) {
          return x;
        } else {
          int z = Double.compare(first.floatZ(), second.floatZ());
          return z;
        }
      }
    }

  }

}

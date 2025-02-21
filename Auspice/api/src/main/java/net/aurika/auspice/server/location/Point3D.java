package net.aurika.auspice.server.location;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

public interface Point3D extends Comparable<Point3D> {
    double getX();

    double getY();

    double getZ();

    default int compareTo(@NotNull Point3D other) {
        Objects.requireNonNull(other);
        return NaturalComparator.INSTANCE.compare(this, other);
    }

    final class NaturalComparator implements Comparator<Point3D> {
        @NotNull
        public static final NaturalComparator INSTANCE = new NaturalComparator();

        private NaturalComparator() {
        }

        public int compare(@NotNull Point3D first, @NotNull Point3D second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            int y = Double.compare(first.getY(), second.getY());
            if (y != 0) {
                return y;
            } else {
                int x = Double.compare(first.getX(), second.getX());
                if (x != 0) {
                    return x;
                } else {
                    int z = Double.compare(first.getZ(), second.getZ());
                    return z;
                }
            }
        }
    }
}

package net.aurika.auspice.server.location;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public interface BlockPoint3D extends Comparable<BlockPoint3D>, Examinable {
    int getX();

    int getY();

    int getZ();

    default double distance(@NotNull BlockPoint3D o) {
        Objects.requireNonNull(o);
        return Math.sqrt(this.distanceSquared(o));
    }

    default double distanceSquared(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other, "other");
        double $this$squared$ivf = (double) this.getX() + (double) other.getX();
        double var10000 = $this$squared$ivf * $this$squared$ivf;
        int $this$squared$iv = this.getY() + other.getY();
        var10000 += $this$squared$iv * $this$squared$iv;
        $this$squared$iv = this.getZ() + other.getZ();
        return var10000 + (double) ($this$squared$iv * $this$squared$iv);
    }

    default int compareTo(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other);
        return NaturalComparator.INSTANCE.compare(this, other);
    }

    @Override
    default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("x", getX()),
                ExaminableProperty.of("y", getY()),
                ExaminableProperty.of("z", getZ())
        );
    }

    final class AxisComparator implements Comparator<BlockPoint3D> {
        private final boolean x;
        private final boolean y;
        private final boolean z;

        public AxisComparator(boolean x, boolean y, boolean z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int compare(@NotNull BlockPoint3D first, @NotNull BlockPoint3D second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            int z;
            if (this.y) {
                z = Intrinsics.compare(first.getY(), second.getY());
                if (z != 0) {
                    return z;
                }
            }

            if (this.x) {
                z = Intrinsics.compare(first.getX(), second.getX());
                if (z != 0) {
                    return z;
                }
            }

            if (this.z) {
                z = Intrinsics.compare(first.getZ(), second.getZ());
                return z;
            }

            return 0;
        }
    }

    final class NaturalComparator implements Comparator<BlockPoint3D> {
        @NotNull
        public static final NaturalComparator INSTANCE = new NaturalComparator();

        private NaturalComparator() {
        }

        public int compare(@NotNull BlockPoint3D first, @NotNull BlockPoint3D second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            int y = Intrinsics.compare(first.getY(), second.getY());
            if (y != 0) {
                return y;
            } else {
                int x = Intrinsics.compare(first.getX(), second.getX());
                if (x != 0) {
                    return x;
                } else {
                    int z = Intrinsics.compare(first.getZ(), second.getZ());
                    return z;
                }
            }
        }
    }
}


package top.auspice.server.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.data.object.DataStringRepresentation;
import top.auspice.utils.string.CommaDataSplitStrategy;

import java.util.Objects;

public class BlockVector3 implements BlockPoint3D, DataStringRepresentation {
    private final int x;
    private final int y;
    private final int z;

    public BlockVector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @NotNull
    public String asDataString() {
        Object[] var1 = new Object[]{this.getX(), this.getY(), this.getZ()};
        String var10000 = CommaDataSplitStrategy.toString(var1);
        Objects.requireNonNull(var10000);
        return var10000;
    }

    @NotNull
    public final BlockVector2 getChunkLocation() {
        return BlockVector2.of(this.getX() >> 4, this.getZ() >> 4);
    }

    @NotNull
    public final BlockVector3 add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(z);
        return of(this.getX() + x.intValue(), this.getY() + y.intValue(), this.getZ() + z.intValue());
    }

    @NotNull
    public final BlockVector3 add(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other);
        return this.add(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public final BlockVector3 subtract(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other);
        return this.subtract(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public final BlockVector3 subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(z);
        return of(this.getX() - x.intValue(), this.getY() - y.intValue(), this.getZ() - z.intValue());
    }

    @NotNull
    public final Vector3 divide(@NotNull Number by) {
        Objects.requireNonNull(by);
        return Vector3.of(this.getX() / by.intValue(), this.getY() / by.intValue(), this.getZ() / by.intValue());
    }

    public final double length() {
        return Math.sqrt(this.lengthSq());
    }

    @NotNull
    public final Vector3 toVector() {
        return Vector3.of(this.getX(), this.getY(), this.getZ());
    }

    @NotNull
    public final BlockLocation3 inWorld(@NotNull World world) {
        Objects.requireNonNull(world);
        return BlockLocation3.of(world, this.getX(), this.getY(), this.getZ());
    }

    public final double lengthSq() {
        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }

    public final boolean containedWithin(@NotNull BlockVector3 min, @NotNull BlockVector3 max) {
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);
        return this.getX() >= min.getX() && this.getX() <= max.getX() && this.getY() >= min.getY() && this.getY() <= max.getY() && this.getZ() >= min.getZ() && this.getZ() <= max.getZ();
    }

    @NotNull
    public final BlockVector3 getMinimum(@NotNull BlockVector3 v2) {
        Objects.requireNonNull(v2);
        return of(Math.min(this.getX(), v2.getX()), Math.min(this.getY(), v2.getY()), Math.min(this.getZ(), v2.getZ()));
    }

    @NotNull
    public final BlockVector3 getMaximum(@NotNull BlockVector3 v2) {
        Objects.requireNonNull(v2);
        return of(Math.max(this.getX(), v2.getX()), Math.max(this.getY(), v2.getY()), Math.max(this.getZ(), v2.getZ()));
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof BlockVector3)) {
            return false;
        } else {
            return this.getX() == ((BlockVector3) other).getX() && this.getY() == ((BlockVector3) other).getY() && this.getZ() == ((BlockVector3) other).getZ();
        }
    }

    public int hashCode() {
        int prime = 31;
        int result = 14;
        result = prime * result + this.getX();
        result = prime * result + this.getY();
        result = prime * result + this.getZ();
        return result;
    }

    @NotNull
    public String toString() {
        return "BlockVector3(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ')';
    }

    @NotNull
    public static BlockVector3 of(int x, int y, int z) {
        return new BlockVector3(x, y, z);
    }

    @NotNull
    public static BlockVector3 of(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other);
        return of(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public static BlockVector3 fromString(@NotNull String str) {
        Objects.requireNonNull(str);
        CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 3);
        return new BlockVector3($this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt());
    }

}

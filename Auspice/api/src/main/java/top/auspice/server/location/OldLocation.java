package top.auspice.server.location;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OldLocation implements Directional, WorldContainer, Point3D {
    @NotNull
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public OldLocation(@NotNull World world, double x, double y, double z, float yaw, float pitch) {
        Objects.requireNonNull(world);
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @NotNull
    public World getWorld() {
        return this.world;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof OldLocation)) {
            return false;
        } else {
            return Intrinsics.areEqual(this.getWorld(), ((OldLocation) other).getWorld()) && this.getX() == ((OldLocation) other).getX() && this.getY() == ((OldLocation) other).getY() && this.getZ() == ((OldLocation) other).getZ() && this.getYaw() == ((OldLocation) other).getYaw() && this.getPitch() == ((OldLocation) other).getPitch();
        }
    }

    @NotNull
    public String toString() {
        return "Location(world=" + this.getWorld() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ')';
    }

    @NotNull
    public final OldLocation add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(z);
        return this.simpleAdd(x, y, z);
    }

    @NotNull
    public final OldLocation add(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other);
        return this.add(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public final OldLocation add(@NotNull Point3D other) {
        Objects.requireNonNull(other);
        return this.add(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public final OldLocation subtract(@NotNull BlockPoint3D other) {
        Objects.requireNonNull(other);
        return this.subtract(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public final OldLocation subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(z);
        return this.simpleAdd(-x.doubleValue(), -y.doubleValue(), -z.doubleValue());
    }

    @NotNull
    public final BlockVector3 toBlockVector() {
        return BlockVector3.of((int) this.getX(), (int) this.getY(), (int) this.getZ());
    }

    @NotNull
    public final Vector3Location toVectorLocation() {
        return Vector3Location.of(this.getWorld(), this.getX(), this.getY(), this.getZ());
    }

    @NotNull
    public final Vector3 toVector() {
        return Vector3.of(this.getX(), this.getY(), this.getZ());
    }

    private OldLocation simpleAdd(Number x, Number y, Number z) {
        return of(this.getWorld(), this.getX() + x.doubleValue(), this.getY() + y.doubleValue(), this.getZ() + z.doubleValue(), this.getYaw(), this.getPitch());
    }

    @NotNull
    public static Void modify() {
        throw new UnsupportedOperationException("Cannot modify immutable location");
    }

    @NotNull
    public static OldLocation of(@NotNull World world, double x, double y, double z, float yaw, float pitch) {
        return new OldLocation(world, x, y, z, yaw, pitch);
    }

}

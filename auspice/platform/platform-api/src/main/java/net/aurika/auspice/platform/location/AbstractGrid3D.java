package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.utils.string.CommaDataSplitStrategy;
import net.aurika.ecliptor.object.DataStringRepresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AbstractGrid3D implements Grid3D, DataStringRepresentation {

  private final int x;
  private final int y;
  private final int z;

  public AbstractGrid3D(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public int gridX() {
    return this.x;
  }

  @Override
  public int gridY() {
    return this.y;
  }

  @Override
  public int gridZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] var1 = new Object[]{this.gridX(), this.gridY(), this.gridZ()};
    String var10000 = CommaDataSplitStrategy.toString(var1);
    Objects.requireNonNull(var10000);
    return var10000;
  }

  @NotNull
  public final AbstractGrid2D getChunkLocation() {
    return AbstractGrid2D.of(this.gridX() >> 4, this.gridZ() >> 4);
  }

  @NotNull
  public final AbstractGrid3D add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return of(this.gridX() + x.intValue(), this.gridY() + y.intValue(), this.gridZ() + z.intValue());
  }

  @NotNull
  public final AbstractGrid3D add(@NotNull Grid3D other) {
    Objects.requireNonNull(other);
    return this.add(other.gridX(), other.gridY(), other.gridZ());
  }

  @NotNull
  public final AbstractGrid3D subtract(@NotNull Grid3D other) {
    Objects.requireNonNull(other);
    return this.subtract(other.gridX(), other.gridY(), other.gridZ());
  }

  @NotNull
  public final AbstractGrid3D subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return of(this.gridX() - x.intValue(), this.gridY() - y.intValue(), this.gridZ() - z.intValue());
  }

  @NotNull
  public final AbstractFloat3D divide(@NotNull Number by) {
    Objects.requireNonNull(by);
    return AbstractFloat3D.of(this.gridX() / by.intValue(), this.gridY() / by.intValue(), this.gridZ() / by.intValue());
  }

  public final double length() {
    return Math.sqrt(this.lengthSq());
  }

  @NotNull
  public final AbstractFloat3D toVector() {
    return AbstractFloat3D.of(this.gridX(), this.gridY(), this.gridZ());
  }

  @NotNull
  public final Grid3DLocation inWorld(@NotNull World world) {
    Objects.requireNonNull(world);
    return Grid3DLocation.of(world, this.gridX(), this.gridY(), this.gridZ());
  }

  public final double lengthSq() {
    return this.gridX() * this.gridX() + this.gridY() * this.gridY() + this.gridZ() * this.gridZ();
  }

  public final boolean containedWithin(@NotNull AbstractGrid3D min, @NotNull AbstractGrid3D max) {
    Objects.requireNonNull(min);
    Objects.requireNonNull(max);
    return this.gridX() >= min.gridX() && this.gridX() <= max.gridX() && this.gridY() >= min.gridY() && this.gridY() <= max.gridY() && this.gridZ() >= min.gridZ() && this.gridZ() <= max.gridZ();
  }

  @NotNull
  public final AbstractGrid3D getMinimum(@NotNull AbstractGrid3D v2) {
    Objects.requireNonNull(v2);
    return of(
        Math.min(this.gridX(), v2.gridX()), Math.min(this.gridY(), v2.gridY()), Math.min(this.gridZ(), v2.gridZ()));
  }

  @NotNull
  public final AbstractGrid3D getMaximum(@NotNull AbstractGrid3D v2) {
    Objects.requireNonNull(v2);
    return of(
        Math.max(this.gridX(), v2.gridX()), Math.max(this.gridY(), v2.gridY()), Math.max(this.gridZ(), v2.gridZ()));
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof AbstractGrid3D)) {
      return false;
    } else {
      return this.gridX() == ((AbstractGrid3D) other).gridX() && this.gridY() == ((AbstractGrid3D) other).gridY() && this.gridZ() == ((AbstractGrid3D) other).gridZ();
    }
  }

  public int hashCode() {
    int prime = 31;
    int result = 14;
    result = prime * result + this.gridX();
    result = prime * result + this.gridY();
    result = prime * result + this.gridZ();
    return result;
  }

  @NotNull
  public String toString() {
    return "BlockVector3(" + this.gridX() + ", " + this.gridY() + ", " + this.gridZ() + ')';
  }

  @NotNull
  public static AbstractGrid3D of(int x, int y, int z) {
    return new AbstractGrid3D(x, y, z);
  }

  @NotNull
  public static AbstractGrid3D of(@NotNull Grid3D other) {
    Objects.requireNonNull(other);
    return of(other.gridX(), other.gridY(), other.gridZ());
  }

  @NotNull
  public static AbstractGrid3D fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 3);
    return new AbstractGrid3D(
        $this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt(),
        $this$fromString_u24lambda_u240.nextInt()
    );
  }

}

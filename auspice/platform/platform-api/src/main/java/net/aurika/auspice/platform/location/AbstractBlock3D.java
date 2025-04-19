package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.utils.string.CommaDataSplitStrategy;
import net.aurika.ecliptor.object.DataStringRepresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AbstractBlock3D implements Block3D, DataStringRepresentation {

  private final int x;
  private final int y;
  private final int z;

  public AbstractBlock3D(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int intX() {
    return this.x;
  }

  public int intY() {
    return this.y;
  }

  public int intZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] var1 = new Object[]{this.intX(), this.intY(), this.intZ()};
    String var10000 = CommaDataSplitStrategy.toString(var1);
    Objects.requireNonNull(var10000);
    return var10000;
  }

  @NotNull
  public final AbstractBlock2D getChunkLocation() {
    return AbstractBlock2D.of(this.intX() >> 4, this.intZ() >> 4);
  }

  @NotNull
  public final AbstractBlock3D add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return of(this.intX() + x.intValue(), this.intY() + y.intValue(), this.intZ() + z.intValue());
  }

  @NotNull
  public final AbstractBlock3D add(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.add(other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public final AbstractBlock3D subtract(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.subtract(other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public final AbstractBlock3D subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return of(this.intX() - x.intValue(), this.intY() - y.intValue(), this.intZ() - z.intValue());
  }

  @NotNull
  public final AbstractFloat3D divide(@NotNull Number by) {
    Objects.requireNonNull(by);
    return AbstractFloat3D.of(this.intX() / by.intValue(), this.intY() / by.intValue(), this.intZ() / by.intValue());
  }

  public final double length() {
    return Math.sqrt(this.lengthSq());
  }

  @NotNull
  public final AbstractFloat3D toVector() {
    return AbstractFloat3D.of(this.intX(), this.intY(), this.intZ());
  }

  @NotNull
  public final BlockLocation3 inWorld(@NotNull World world) {
    Objects.requireNonNull(world);
    return BlockLocation3.of(world, this.intX(), this.intY(), this.intZ());
  }

  public final double lengthSq() {
    return this.intX() * this.intX() + this.intY() * this.intY() + this.intZ() * this.intZ();
  }

  public final boolean containedWithin(@NotNull AbstractBlock3D min, @NotNull AbstractBlock3D max) {
    Objects.requireNonNull(min);
    Objects.requireNonNull(max);
    return this.intX() >= min.intX() && this.intX() <= max.intX() && this.intY() >= min.intY() && this.intY() <= max.intY() && this.intZ() >= min.intZ() && this.intZ() <= max.intZ();
  }

  @NotNull
  public final AbstractBlock3D getMinimum(@NotNull AbstractBlock3D v2) {
    Objects.requireNonNull(v2);
    return of(Math.min(this.intX(), v2.intX()), Math.min(this.intY(), v2.intY()), Math.min(this.intZ(), v2.intZ()));
  }

  @NotNull
  public final AbstractBlock3D getMaximum(@NotNull AbstractBlock3D v2) {
    Objects.requireNonNull(v2);
    return of(Math.max(this.intX(), v2.intX()), Math.max(this.intY(), v2.intY()), Math.max(this.intZ(), v2.intZ()));
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof AbstractBlock3D)) {
      return false;
    } else {
      return this.intX() == ((AbstractBlock3D) other).intX() && this.intY() == ((AbstractBlock3D) other).intY() && this.intZ() == ((AbstractBlock3D) other).intZ();
    }
  }

  public int hashCode() {
    int prime = 31;
    int result = 14;
    result = prime * result + this.intX();
    result = prime * result + this.intY();
    result = prime * result + this.intZ();
    return result;
  }

  @NotNull
  public String toString() {
    return "BlockVector3(" + this.intX() + ", " + this.intY() + ", " + this.intZ() + ')';
  }

  @NotNull
  public static AbstractBlock3D of(int x, int y, int z) {
    return new AbstractBlock3D(x, y, z);
  }

  @NotNull
  public static AbstractBlock3D of(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return of(other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public static AbstractBlock3D fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy $this$fromString_u24lambda_u240 = new CommaDataSplitStrategy(str, 3);
    return new AbstractBlock3D(
        $this$fromString_u24lambda_u240.nextInt(), $this$fromString_u24lambda_u240.nextInt(),
        $this$fromString_u24lambda_u240.nextInt()
    );
  }

}

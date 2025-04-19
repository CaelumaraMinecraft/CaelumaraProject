package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AbstractFloat3D implements Float3D {

  private final double x;
  private final double y;
  private final double z;
  public static final AbstractFloat3D ZERO = of(0, 0, 0);

  public AbstractFloat3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public double floatX() {
    return this.x;
  }

  @Override
  public double floatY() {
    return this.y;
  }

  @Override
  public double floatZ() {
    return this.z;
  }

  @NotNull
  public final AbstractBlock3D toBlockVector() {
    return AbstractBlock3D.of(
        LocationUtils.toBlock(this.floatX()), LocationUtils.toBlock(this.floatY()),
        LocationUtils.toBlock(this.floatZ())
    );
  }

  @NotNull
  public final AbstractFloat3D add(double x, double y, double z) {
    return of(this.floatX() + x, this.floatY() + y, this.floatZ() + z);
  }

  @NotNull
  public final AbstractFloat3D add(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.add(other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public final AbstractFloat3D add(@NotNull Float3D other) {
    Objects.requireNonNull(other);
    return this.add(other.floatX(), other.floatY(), other.floatZ());
  }

  @NotNull
  public final PrecisionLocation inWorld(@NotNull World world) {
    Objects.requireNonNull(world);
    return PrecisionLocation.precisionLocation(world, this.floatX(), this.floatY(), this.floatZ(), 0.0F, 0.0F);
  }

  public final double lengthSq() {
    double x = this.floatX();
    double y = this.floatY();
    double z = this.floatZ();
    return x * x + y * y + z * z;
  }

  @NotNull
  public final AbstractFloat3D getMinimum(@NotNull AbstractFloat3D v2) {
    Objects.requireNonNull(v2);
    return of(
        Math.min(this.floatX(), v2.floatX()), Math.min(this.floatY(), v2.floatY()),
        Math.min(this.floatZ(), v2.floatZ())
    );
  }

  public final double length() {
    return Math.sqrt(this.lengthSq());
  }

  @NotNull
  public final AbstractFloat3D normalize() {
    double len = this.length();
    return of(this.floatX() / len, this.floatY() / len, this.floatZ() / len);
  }

  @NotNull
  public final AbstractFloat3D withX(@NotNull Number x) {
    Objects.requireNonNull(x);
    return of(x, this.floatY(), this.floatZ());
  }

  @NotNull
  public final AbstractFloat3D withY(@NotNull Number y) {
    Objects.requireNonNull(y);
    return of(this.floatX(), y, this.floatZ());
  }

  @NotNull
  public final AbstractFloat3D withZ(@NotNull Number z) {
    Objects.requireNonNull(z);
    return of(this.floatX(), this.floatY(), z);
  }

  @NotNull
  public final AbstractFloat3D divide(@NotNull Number by0) {
    Objects.requireNonNull(by0);
    double d = by0.doubleValue();
    return of(this.floatX() / d, this.floatY() / d, this.floatZ() / d);
  }

  @NotNull
  public final AbstractFloat3D getMaximum(@NotNull AbstractFloat3D v2) {
    Objects.requireNonNull(v2);
    return of(
        Math.max(this.floatX(), v2.floatX()), Math.max(this.floatY(), v2.floatY()),
        Math.max(this.floatZ(), v2.floatZ())
    );
  }

  @NotNull
  public final AbstractFloat3D floor() {
    return of(Math.floor(this.floatX()), Math.floor(this.floatY()), Math.floor(this.floatZ()));
  }

  @NotNull
  public final AbstractFloat3D ceil() {
    return of(Math.ceil(this.floatX()), Math.ceil(this.floatY()), Math.ceil(this.floatZ()));
  }

  public final double dot(@NotNull AbstractFloat3D other) {
    Objects.requireNonNull(other);
    return this.floatX() * other.floatX() + this.floatY() * other.floatY() + this.floatZ() * other.floatZ();
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof AbstractFloat3D)) {
      return false;
    } else {
      return this.floatX() == ((AbstractFloat3D) other).floatX() && this.floatY() == ((AbstractFloat3D) other).floatY() && this.floatZ() == ((AbstractFloat3D) other).floatZ();
    }
  }

  public @NotNull String toString() {
    return "Vector3(" + this.floatX() + ", " + this.floatY() + ", " + this.floatZ() + ')';
  }

  @NotNull
  public static AbstractFloat3D of(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return new AbstractFloat3D(other.intX(), other.intY(), other.intZ());
  }

  public static @NotNull AbstractFloat3D of(@NotNull Float3D other) {
    Validate.Arg.notNull(other, "other");
    return new AbstractFloat3D(other.floatX(), other.floatY(), other.floatZ());
  }

  public static @NotNull AbstractFloat3D of(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    return new AbstractFloat3D(x.doubleValue(), y.doubleValue(), z.doubleValue());
  }

  public static @NotNull AbstractFloat3D fromString(@NotNull String str) {
    Validate.Arg.notNull(str, "str");
    CommaDataSplitStrategy fromString = new CommaDataSplitStrategy(str, 3);
    return new AbstractFloat3D(fromString.nextDouble(), fromString.nextDouble(), fromString.nextDouble());
  }

}

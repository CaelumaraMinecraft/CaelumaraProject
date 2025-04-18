package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class LocationUtils {

  public static final double PII = 6.283185307179586;

  private LocationUtils() {
  }

  public static int toBlock(double num) {
    int floor = (int) num;
    return (double) floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
  }

  @NotNull
  public static Directional fromDirection(@NotNull AbstractFloat3D vector) {
    Objects.requireNonNull(vector);
    double x = vector.floatX();
    double y = vector.floatY();
    double z = vector.floatZ();
    if (x == 0.0 && z == 0.0) {
      return Directional.of(0.0F, y > 0.0 ? -90.0F : 90.0F);
    } else {
      double theta = Math.atan2(-x, z);
      float yaw = (float) Math.toDegrees((theta + 6.283185307179586) % 6.283185307179586);
      double x2 = x * x;
      double z2 = z * z;
      double xz = Math.sqrt(x2 + z2);
      float pitch = (float) Math.toDegrees(Math.atan(-y / xz));
      return Directional.of(yaw, pitch);
    }
  }

}

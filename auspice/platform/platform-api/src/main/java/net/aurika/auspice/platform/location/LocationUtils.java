package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.location.abstraction.Float3Pos;
import net.aurika.auspice.platform.location.direction.Directional;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class LocationUtils {

  private static final double PII = 6.283185307179586;

  private LocationUtils() { }

  public static int toBlock(double num) {
    int floor = (int) num;
    return (double) floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
  }

  @Deprecated
  public static @NotNull Directional fromDirection(@NotNull Float3Pos float3Pos) {
    Validate.Arg.notNull(float3Pos, "float3Pos");
    double x = float3Pos.floatX();
    double y = float3Pos.floatY();
    double z = float3Pos.floatZ();
    if (x == 0.0 && z == 0.0) {
      return Directional.directional(y > 0.0 ? -90.0F : 90.0F, 0.0F);
    } else {
      double theta = Math.atan2(-x, z);
      float yaw = (float) Math.toDegrees((theta + PII) % PII);
      double x2 = x * x;
      double z2 = z * z;
      double xz = Math.sqrt(x2 + z2);
      float pitch = (float) Math.toDegrees(Math.atan(-y / xz));
      return Directional.directional(pitch, yaw);
    }
  }

}

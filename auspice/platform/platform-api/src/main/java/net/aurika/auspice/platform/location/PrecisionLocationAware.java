package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface PrecisionLocationAware extends WorldAware, FloatXAware, FloatYAware, FloatZAware, PitchAware, YawAware {

  @Contract("_ -> new")
  static <T extends WorldAware & FloatXAware & FloatYAware & FloatZAware & PitchAware & YawAware> @NotNull PrecisionLocation precisionLocation(@NotNull T obj) {
    Validate.Arg.notNull(obj, "obj");
    return new PrecisionLocation(obj.world(), obj.floatX(), obj.floatY(), obj.floatZ(), obj.pitch(), obj.yaw());
  }

  @Override
  @NotNull World world();

  @Override
  double floatX();

  @Override
  double floatY();

  @Override
  double floatZ();

  @Override
  float pitch();

  @Override
  float yaw();

  default @NotNull PrecisionLocation locationCopy() {
    return new PrecisionLocation(world(), floatX(), floatY(), floatZ(), pitch(), yaw());
  }

}

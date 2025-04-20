package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface LocationAware extends WorldAware, FloatXAware, FloatYAware, FloatZAware, PitchAware, YawAware {

  @Contract("_ -> new")
  static <T extends WorldAware & FloatXAware & FloatYAware & FloatZAware & PitchAware & YawAware> @NotNull AbstractLocationMutable precisionLocation(@NotNull T obj) {
    Validate.Arg.notNull(obj, "obj");
    return new AbstractLocationMutable(obj.world(), obj.floatX(), obj.floatY(), obj.floatZ(), obj.pitch(), obj.yaw());
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

  default @NotNull AbstractLocationMutable locationCopy() {
    return new AbstractLocationMutable(world(), floatX(), floatY(), floatZ(), pitch(), yaw());
//    return precisionLocation(this);
  }

}

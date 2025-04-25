package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LocationAware extends WorldAware, FloatXAware, FloatYAware, FloatZAware, PitchAware, YawAware {

  @Contract("_ -> new") // precision
  static <T extends WorldAware & FloatXAware & FloatYAware & FloatZAware & PitchAware & YawAware> @NotNull Location location(@NotNull T locationAware) {
    Validate.Arg.notNull(locationAware, "locationAware");
    return Location.location(
        locationAware.world(),
        locationAware.floatX(), locationAware.floatY(), locationAware.floatZ(),
        locationAware.pitch(), locationAware.yaw()
    );
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

  default @NotNull Location locationCopy() {
    return Location.location(world(), floatX(), floatY(), floatZ(), pitch(), yaw());
  }

  /**
   * Sets the location of the entity to the mutable location.
   *
   * @param location the mutable location
   * @return the first parameter
   */
  @Contract("_ -> param1")
  default LocationMutable joinLocation(@Nullable LocationMutable location) {
    if (location != null) {
      location.world(world());
      location.floatX(floatX());
      location.floatY(floatY());
      location.floatZ(floatZ());
      location.pitch(pitch());
      location.yaw(yaw());
    }
    return location;
  }

}

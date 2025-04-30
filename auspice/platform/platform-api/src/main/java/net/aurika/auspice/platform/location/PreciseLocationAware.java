package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.location.direction.PitchAware;
import net.aurika.auspice.platform.location.direction.YawAware;
import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.LocationProperties.*;

public interface PreciseLocationAware extends WorldAware, PitchAware, YawAware, Examinable {

  @Override
  @ExaminablePropertyGetter(VAL_WORLD)
  @NotNull World world();

  @ExaminablePropertyGetter(VAL_X)
  double preciseX();

  @ExaminablePropertyGetter(VAL_Y)
  double preciseY();

  @ExaminablePropertyGetter(VAL_Z)
  double preciseZ();

  @Override
  @ExaminablePropertyGetter(VAL_PITCH)
  float pitch();

  @Override
  @ExaminablePropertyGetter(VAL_YAW)
  float yaw();

  /**
   * Sets the location of the entity to the mutable location.
   *
   * @param location the mutable location
   * @return the first parameter
   */
  @Contract("_ -> param1")
  @Deprecated
  default PreciseLocationMutable joinLocation(@Nullable PreciseLocationMutable location) {
    if (location != null) {
      location.world(world());
      location.preciseX(preciseX());
      location.preciseY(preciseY());
      location.preciseZ(preciseZ());
      location.pitch(pitch());
      location.yaw(yaw());
    }
    return location;
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_WORLD, world()),
        ExaminableProperty.of(VAL_X, preciseX()),
        ExaminableProperty.of(VAL_Y, preciseY()),
        ExaminableProperty.of(VAL_Z, preciseZ()),
        ExaminableProperty.of(VAL_YAW, yaw()),
        ExaminableProperty.of(VAL_PITCH, pitch())
    );
  }

}

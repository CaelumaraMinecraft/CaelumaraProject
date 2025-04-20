package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import org.jetbrains.annotations.NotNull;

public interface Location extends WorldAware, Float3D, Directional {

  @Override
  @NotNull
  World world();

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

}

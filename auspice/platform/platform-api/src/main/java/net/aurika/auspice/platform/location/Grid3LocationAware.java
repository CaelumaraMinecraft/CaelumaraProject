package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Grid3LocationAware extends WorldAware, GridXAware, GridYAware, GridZAware {

  @Override
  @NotNull World world();

  @Override
  int gridX();

  @Override
  int gridY();

  @Override
  int gridZ();

  @Contract(value = "-> new", pure = true)
  default @NotNull Grid3Location grid3Location() {
    return Grid3Location.grid3Location(world(), gridX(), gridY(), gridZ());
  }

}

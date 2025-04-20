package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldMutable;
import org.jetbrains.annotations.NotNull;

public interface LocationMutable extends Location, WorldMutable, Float3DMutable, DirectionalMutable {

  @Override
  @NotNull World world();

  @Override
  void world(@NotNull World world);

  @Override
  double floatX();

  @Override
  void floatX(double x);

  @Override
  double floatY();

  @Override
  void floatY(double y);

  @Override
  double floatZ();

  @Override
  void floatZ(double z);

  @Override
  float pitch();

  @Override
  void pitch(float pitch);

  @Override
  float yaw();

  @Override
  void yaw(float yaw);

}

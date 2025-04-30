package net.aurika.auspice.platform.location.abstraction;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.LocationProperties.VAL_WORLD;

public interface Grid2Location extends WorldAware, Grid2Pos {

  @Override
  @NotNull World world();

  @Override
  int gridX();

  @Override
  int gridZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.concat(
        Stream.of(ExaminableProperty.of(VAL_WORLD, world())),  // world
        Grid2Pos.super.examinableProperties()                  // x, z
    );
  }

}

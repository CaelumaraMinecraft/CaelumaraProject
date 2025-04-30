package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import org.jetbrains.annotations.NotNull;

public interface SectionLocationLike extends WorldAware {

  @Override
  @NotNull World world();

  int sectionX();

  int sectionY();

  int sectionZ();

}

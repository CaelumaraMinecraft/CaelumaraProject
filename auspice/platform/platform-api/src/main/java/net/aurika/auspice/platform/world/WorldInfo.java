package net.aurika.auspice.platform.world;

import net.aurika.auspice.platform.DoubleIdentified;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface WorldInfo extends DoubleIdentified {

  /**
   * Gets the unique name of the world.
   *
   * @return the unique name
   */
  @Override
  @NotNull String name();

  /**
   * Gets the unique id of the world.
   *
   * @return the unique id
   */
  @Override
  @NotNull UUID uuid();

}

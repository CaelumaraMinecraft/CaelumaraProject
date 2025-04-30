package net.aurika.auspice.platform.world;

import net.aurika.auspice.platform.UUIDIdentified;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface WorldInfo extends UUIDIdentified {

  /**
   * Gets the unique id of the world.
   *
   * @return the unique id
   */
  @Override
  @NotNull UUID uniqueId();

  /**
   * Gets the unique name of the world.
   *
   * @return the unique name
   */
  @NotNull String name();

}

package net.aurika.auspice.platform;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface UniqueIdentified {

  /**
   * Get the unique identity of the object.
   *
   * @return the unique id
   */
  @NotNull UUID uniqueId();

}

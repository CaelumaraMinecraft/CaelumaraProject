package net.aurika.auspice.platform.entity.abstraction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AnimalTamer {

  /**
   * Gets the name of the {@code AnimalTamer}.
   *
   * @return The name to reference on tamed animals or null if a name cannot be obtained
   */
  @Nullable String name();

  @NotNull UUID uuid();

}

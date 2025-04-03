package net.aurika.auspice.service.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Service {

  default @Nullable Throwable checkAvailability() {
    return null;
  }

  /**
   * Gets the service name.
   *
   * @return the service name
   */
  default @NotNull String serviceName() {
    return this.getClass().getSimpleName();
  }

  /**
   * Enables this service.
   */
  default void enable() { }

  /**
   * Disables this service.
   */
  default void disable() { }

}

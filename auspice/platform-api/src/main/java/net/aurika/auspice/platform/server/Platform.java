package net.aurika.auspice.platform.server;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A platform.
 */
public interface Platform {

  static void init(Platform instance) {
    PlatformContainer.INSTANCE = instance;
  }

  /**
   * Gets the platform instance.
   *
   * @return the platform instance
   * @throws IllegalStateException when the server was not initialized or has other problems
   */
  @Contract(pure = true)
  static @NotNull Platform get() throws IllegalStateException {
    Platform platform = PlatformContainer.INSTANCE;
    if (platform == null) {
      throw new IllegalStateException("Server instance not initiated yet");
    }
    return platform;
  }

  default void onStartup() {
  }

  default void onReady() {
  }

  default void onEnable() {
  }

}
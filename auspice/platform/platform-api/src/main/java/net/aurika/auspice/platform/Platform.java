package net.aurika.auspice.platform;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A platform.
 */
public interface Platform {

  /**
   * Initializes the platform when the platform startup.
   *
   * @param platform the platform that auspice running on
   */
  @ApiStatus.Internal
  static void init(Platform platform) {
    PlatformContainer.INSTANCE = platform;
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
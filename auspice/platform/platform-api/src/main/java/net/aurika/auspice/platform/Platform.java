package net.aurika.auspice.platform;

import net.aurika.auspice.platform.adapter.AdapterRegistry;
import net.aurika.auspice.platform.registry.RegistryContainer;
import net.aurika.auspice.platform.world.WorldRegistry;
import net.aurika.common.annotation.container.ThrowOnAbsent;
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
  public static void init(Platform platform) { Platform$Companion.PLATFORM = platform; }

  /**
   * Gets the platform instance.
   *
   * @return the platform instance
   * @throws IllegalStateException when the platform was not initialized
   */
  @Contract(pure = true)
  @ThrowOnAbsent
  public static @NotNull Platform get() throws IllegalStateException {
    Platform platform = Platform$Companion.PLATFORM;
    if (platform == null) {
      throw new IllegalStateException("Server instance not initiated yet");
    }
    return platform;
  }

  /**
   * Gets adapters of the platform.
   *
   * @return the adapter registry
   */
  @NotNull AdapterRegistry adapters();

  /**
   * Gets registries of the platform.
   *
   * @return the registries
   */
  @NotNull RegistryContainer registries();

  /**
   * Gets the worlds on the platform.
   *
   * @return the worlds
   */
  @NotNull WorldRegistry worldRegistry();

  default void onStartup() { }

  default void onReady() { }

  default void onEnable() { }

}

final class Platform$Companion {

  static Platform PLATFORM = null;

  private Platform$Companion() { }

}
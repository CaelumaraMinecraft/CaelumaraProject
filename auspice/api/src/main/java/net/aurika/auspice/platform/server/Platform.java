package net.aurika.auspice.platform.server;

/**
 * A platform.
 */
public interface Platform {

  static void init(Platform instance) {
    ServerContainer.INSTANCE = instance;
  }

  /**
   * Gets the server instance.
   *
   * @return the server instance
   * @throws IllegalStateException when the server was not initialized or has other problems
   */
  static Platform get() throws IllegalStateException {
    Platform platform = ServerContainer.INSTANCE;
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
package net.aurika.auspice.platform.server;

import net.aurika.auspice.event.api.MinecraftEventManager;
import net.aurika.auspice.platform.Platform;
import org.jetbrains.annotations.NotNull;

/**
 * A server that supports event system.
 */
public interface EventServer extends Platform {

  @NotNull MinecraftEventManager eventManager();

}

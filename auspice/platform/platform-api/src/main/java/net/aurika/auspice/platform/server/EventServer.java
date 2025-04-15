package net.aurika.auspice.platform.server;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.event.EventManager;
import org.jetbrains.annotations.NotNull;

/**
 * A server that supports event system.
 */
public interface EventServer extends Platform {

  @NotNull EventManager eventManager();

}

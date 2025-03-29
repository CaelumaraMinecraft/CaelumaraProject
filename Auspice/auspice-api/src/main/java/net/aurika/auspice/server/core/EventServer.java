package net.aurika.auspice.server.core;

import net.aurika.auspice.server.event.EventManager;
import org.jetbrains.annotations.NotNull;

/**
 * A server that supports event system.
 */
public interface EventServer extends Server {

  @NotNull EventManager eventManager();

}

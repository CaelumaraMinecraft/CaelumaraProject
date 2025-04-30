package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An event manager to manage events, conduits and event transports.
 *
 * @param <BE> the base event type
 */
public interface EventManager<BE extends Event> {

  /**
   * Calls an {@code event} to the conduits.
   *
   * @param event the event to call
   */
  void callEvent(@NotNull BE event);

  /**
   * Gets the emitters.
   *
   * @return the emitter collection
   */
  @NotNull Collection<? extends Conduit<? extends BE>> conduits();

  void addConduit(@NotNull Conduit<? extends BE> conduit);

}

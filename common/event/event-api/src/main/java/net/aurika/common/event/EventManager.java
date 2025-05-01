package net.aurika.common.event;

import net.aurika.common.annotation.container.ThrowOnAbsent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

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

  @NotNull Class<? extends BE> baseEventType();

  /**
   * Gets the conduits in the event manager.
   *
   * @return the event conduits
   */
  @NotNull Set<? extends Conduit<? extends BE>> conduits();

  boolean hasConduit(@NotNull Class<? extends BE> eventType);

  /**
   * Gets a conduit by the event type.
   *
   * @param eventType the event type
   * @return the conduit
   */
  @ThrowOnAbsent
  @NotNull Conduit<? extends BE> getConduit(@NotNull Class<? extends BE> eventType);

  void addConduit(@NotNull Conduit<? extends BE> conduit);

}

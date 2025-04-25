package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An event manager to manage events.
 *
 * @param <BE> the base event type
 */
public interface EventManager<BE extends Event> {

  /**
   * Calls an {@code event} to the emitters.
   *
   * @param event the event to call
   */
  void callEvent(@NotNull BE event);

  /**
   * Gets the emitters.
   *
   * @return the emitter collection
   */
  @NotNull Collection<? extends Emitter<? extends BE>> emitters();

  void addEmitter(@NotNull Emitter<? extends BE> emitter);

}

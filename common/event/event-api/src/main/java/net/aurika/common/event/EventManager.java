package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface EventManager {

  /**
   * Calls an {@code event} to the emitters.
   *
   * @param event the event to call
   */
  void callEvent(@NotNull Event event);

  /**
   * Gets the emitters.
   *
   * @return the emitter collection
   */
  @NotNull Collection<? extends Emitter<? extends Event>> emitters();

  void addEmitter(@NotNull Emitter<? extends Event> emitter);

}

package net.aurika.common.event;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Listener<E extends Event> extends Keyed {

  @Override
  @NotNull Key key();

  /**
   * Handles the {@code event}.
   *
   * @param event the event
   */
  void accept(@NotNull E event);

  /**
   * Gets the container of the listener.
   *
   * @return the container
   */
  @NotNull Emitter<? extends E> container();

  boolean ignoreCancelled();

  @NotNull Class<? extends E> listenedEventType();

}

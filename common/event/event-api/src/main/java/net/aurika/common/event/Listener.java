package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A listener to an {@link Event}.
 *
 * @param <E> the event type
 */
public interface Listener<E extends Event> extends Identified {

  @Override
  @Contract(pure = true)
  @NotNull Ident ident();

  /**
   * Handles the {@code event}.
   *
   * @param event the event
   */
  void accept(@NotNull E event);

  /**
   * Gets the listened event type.
   *
   * @return the event type
   */
  @NotNull Class<? extends E> listenedEventType();

}

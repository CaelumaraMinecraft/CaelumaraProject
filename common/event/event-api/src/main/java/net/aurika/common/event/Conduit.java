package net.aurika.common.event;

import net.aurika.common.annotation.container.ThrowOnAbsent;
import net.aurika.common.ident.Ident;
import org.jetbrains.annotations.NotNull;

/**
 * A conduit to transport an {@link Event event}.
 * In a conduit, a listener has lower order, the later the listener calls, and the higher the priority for determining an event.
 *
 * @param <E> the event type
 */
public interface Conduit<E extends Event> {

  /**
   * Transports the {@code event} to registered listeners.
   *
   * @param event the event to transport
   * @throws IllegalArgumentException when the input event type is not a subtype of {@link E}
   */
  void transport(@NotNull E event) throws IllegalArgumentException;

  /**
   * Registers a listener to this container.
   *
   * @param listener the listener
   * @throws IllegalArgumentException the listened event type of {@code listener} is not equals the emitter event type.
   * @throws IllegalArgumentException when the {@code listener} has duplicate ID with existing listeners
   */
  void register(@NotNull Listener<E> listener) throws IllegalArgumentException;

  /**
   * Gets the event class the listener container handled.
   *
   * @return the event type class
   */
  @NotNull Class<E> eventType();

  /**
   * Gets the non-null registered listeners sequence in the event conduit.
   *
   * @return the registered listeners
   */
  @NotNull Listener<E> @NotNull [] listeners();

  @ThrowOnAbsent
  @NotNull Listener<E> getListener(int index) throws IndexOutOfBoundsException;

  @ThrowOnAbsent
  @NotNull Listener<E> getListener(@NotNull Ident id) throws IllegalArgumentException;

}

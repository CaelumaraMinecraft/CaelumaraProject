package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * An emitter to a {@link Event}.
 *
 * @param <E> the event type
 */
public interface Emitter<E extends Event> {

  /**
   * Emits the {@code event} to registered listeners.
   *
   * @param event the event
   */
  void emit(@NotNull E event);

  /**
   * Registers a listener to this container.
   *
   * @param listener the listener
   * @throws IllegalArgumentException when the {@code listener} is not valid,
   *                                  such as the listened event type of {@code listener} is not equals the {@link E event} type.
   */
  void register(@NotNull Listener<E> listener);

  /**
   * Gets the event class the listener container handled.
   *
   * @return the event type class
   */
  @NotNull Class<E> eventType();

  /**
   * Gets the containers directly extended by this listener container.
   *
   * @return the parent containers
   */
  @NotNull Emitter<? super E> @NotNull [] directParentEmitters();

  /**
   * Gets all containers directly or  extended by this listener container.
   *
   * @return the parent containers
   */
  @NotNull Set<@NotNull Emitter<? super E>> allParentEmitters();

  /**
   * Gets the registered listeners in the emitter. On synced listener container, it may be sequenced.
   *
   * @return the registered listeners
   */
  @NotNull Listener<E> @NotNull [] listeners();

}

package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Transformer<E extends Event> {

  /**
   * Transforms the {@code event} to registered listeners.
   *
   * @param event the event
   */
  void transform(@NotNull E event);

  /**
   * Registers a listener to this container.
   *
   * @param listener the listener
   * @throws IllegalArgumentException when the {@code listener} is not valid
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
  @NotNull Transformer<? super E> @NotNull [] directParentTransformers();

  /**
   * Gets all containers directly or  extended by this listener container.
   *
   * @return the parent containers
   */
  @NotNull Set<@NotNull Transformer<? super E>> allParentTransformers();

  /**
   * Gets the registered listeners in the listener container. On synced listener container, it may be sequenced.
   *
   * @return the registered listeners
   */
  @NotNull Listener<E> @NotNull [] listeners();

}

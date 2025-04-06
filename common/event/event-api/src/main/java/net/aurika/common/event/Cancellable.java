package net.aurika.common.event;

/**
 * Marks an {@link Event} can be cancelled and uncancelled.
 */
public interface Cancellable {

  /**
   * Gets whether this event is cancelled.
   *
   * @return if this event is cancelled
   */
  boolean cancelled();

  /**
   * Cancels this event.
   */
  void cancel();

  /**
   * Uncancels this event.
   */
  void uncancel();

}

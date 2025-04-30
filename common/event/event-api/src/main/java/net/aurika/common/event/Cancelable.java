package net.aurika.common.event;

import org.jetbrains.annotations.ApiStatus;

/**
 * Marks an {@link Event} can be canceled and uncanceled.
 */
@ApiStatus.Experimental
public interface Cancelable {

  /**
   * Gets whether this event is canceled.
   *
   * @return if this event is canceled
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

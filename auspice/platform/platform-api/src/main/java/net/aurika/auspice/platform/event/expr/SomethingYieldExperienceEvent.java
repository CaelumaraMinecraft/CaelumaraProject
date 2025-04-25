package net.aurika.auspice.platform.event.expr;

import org.jetbrains.annotations.Range;

/**
 * An event that's called when something yields experience.
 */
public interface SomethingYieldExperienceEvent {

  /**
   * Get the experience dropped by the thing after the event has processed
   *
   * @return The experience to drop
   */
  int experienceToDrop();

  /**
   * Set the amount of experience dropped by the block after the event has
   * processed
   *
   * @param experience 1 or higher to drop experience, else nothing will drop
   */
  void experienceToDrop(@Range(from = 0, to = Integer.MAX_VALUE) int experience);

}

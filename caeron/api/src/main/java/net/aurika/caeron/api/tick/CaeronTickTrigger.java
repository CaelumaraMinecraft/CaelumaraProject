package net.aurika.caeron.api.tick;

/**
 * An object that will be triggered every caeron tick.
 */
public interface CaeronTickTrigger {

  /**
   * @param ticks the caeron ticks
   */
  void onCaeronTick(long ticks);

}

package net.aurika.auspice.platform.block.state;

public interface Beacon extends BlockState {

  /**
   * Returns the tier of the beacon pyramid (0-4). The tier refers to the
   * beacon's power level, based on how many layers of blocks are in the
   * pyramid. Tier 1 refers to a beacon with one layer of 9 blocks under it.
   *
   * @return the beacon tier
   */
  int getTier();


}

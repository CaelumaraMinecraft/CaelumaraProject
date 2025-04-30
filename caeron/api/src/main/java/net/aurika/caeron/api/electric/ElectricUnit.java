package net.aurika.caeron.api.electric;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public enum ElectricUnit {
  /**
   * 1J.
   */
  J(1),
  /**
   * 1kJ = 1000J.
   */
  kJ(1000),
  /**
  * 1MJ = 1000kJ = 1000,000J.
   */
  MJ(1000_000),
  /**
   * 1GJ = 1000MJ = 1000,000kJ = 1000,000,000J.
   */
  GJ(1000_000_000);

  private final long units;
  ElectricUnit(long equalization) {
    this.units = equalization;
  }
}

package net.aurika.util.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Avg {

  private double sum;
  private long count;

  public Avg(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  /**
   * Gets the sum value.
   *
   * @return the sum value
   */
  public final double sum() {
    return this.sum;
  }

  /**
   * Sets the sum value.
   *
   * @param sum the sum value
   */
  public final void sum(double sum) {
    this.sum = sum;
  }

  /**
   * Gets the count value.
   *
   * @return the count value
   */
  public final long count() {
    return this.count;
  }

  /**
   * Sets the count value.
   *
   * @param count the count value
   */
  public final void count(long count) {
    this.count = count;
  }

  @Contract(value = "_ -> this", mutates = "this")
  public final @NotNull Avg plus(double additional) {
    this.sum += additional;
    return this;
  }

  /**
   * Averages returns the averaged value.
   *
   * @return the averaged value
   */
  public final double average() {
    return this.sum / (double) this.count;
  }

}

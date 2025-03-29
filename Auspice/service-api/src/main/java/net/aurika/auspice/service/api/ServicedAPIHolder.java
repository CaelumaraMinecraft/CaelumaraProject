package net.aurika.auspice.service.api;

import org.jetbrains.annotations.NotNull;

/**
 * A utility to hold serviced object.
 *
 * @param <S> the serviced object type
 */
public abstract class ServicedAPIHolder<S> {

  private S serviced;

  public ServicedAPIHolder() {
  }

  public ServicedAPIHolder(S serviced) {
    this.serviced = serviced;
  }

  /**
   * Gets the serviced object.
   *
   * @return the serviced object
   */
  public @NotNull S get() {
    if (serviced == null) {
      throw new IllegalStateException("The serviced API has not been initialized yet");
    }
    return serviced;
  }

  /**
   * Sets the serviced object.
   *
   * @param serviced the serviced object
   */
  protected void set(@NotNull S serviced) {
    this.serviced = serviced;
  }

  /**
   * Initializes the serviced object.
   */
  protected abstract void initServiced();

}

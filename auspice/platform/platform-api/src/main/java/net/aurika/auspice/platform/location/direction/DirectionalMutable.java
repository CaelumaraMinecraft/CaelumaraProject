package net.aurika.auspice.platform.location.direction;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface DirectionalMutable extends Directional, PitchMutable, YawMutable {

  @Contract(value = "_, _ -> new", pure = true)
  static @NotNull DirectionalMutable directionMutable(float pitch, float yaw) {
    return new DirectionalMutableImpl(pitch, yaw);
  }

  @Contract(value = " -> new", pure = true)
  static @NotNull DirectionalMutable zeroDirectionalMutable() {
    return directionMutable(0, 0);
  }

  @Override
  float pitch();

  /**
   * Sets the pitch of the directional.
   *
   * @param pitch the new pitch value
   * @return this
   */
  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull PitchMutable pitch(float pitch);

  @Override
  float yaw();

  /**
   * Sets the yaw of the directional.
   *
   * @param yaw the new yaw value
   * @return this
   */
  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull YawMutable yaw(float yaw);

}

final class DirectionalMutableImpl implements DirectionalMutable {

  private float pitch;
  private float yaw;

  DirectionalMutableImpl(float pitch, float yaw) {
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public float pitch() { return pitch; }

  @Override
  public @NotNull PitchMutable pitch(float pitch) {
    this.pitch = pitch;
    return this;
  }

  @Override
  public float yaw() { return yaw; }

  @Override
  public @NotNull YawMutable yaw(float yaw) {
    this.yaw = yaw;
    return this;
  }

}

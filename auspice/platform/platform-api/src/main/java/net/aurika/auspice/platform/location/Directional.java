package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Directional extends PitchAware, YawAware {

  @Override
  float pitch();

  @Override
  float yaw();

  default @NotNull AbstractFloat3D getDirection() {
    double rotX = this.yaw();
    double rotY = this.pitch();
    double xz = Math.cos(Math.toRadians(rotY));
    return AbstractFloat3D.of(
        -xz * Math.sin(Math.toRadians(rotX)), -Math.sin(Math.toRadians(rotY)), xz * Math.cos(Math.toRadians(rotX)));
  }

  @NotNull
  static Directional of(@NotNull Number yaw, @NotNull Number pitch) {
    Objects.requireNonNull(pitch);
    Objects.requireNonNull(yaw);
    return new AbstractDirectional(pitch.floatValue(), yaw.floatValue());
  }

}

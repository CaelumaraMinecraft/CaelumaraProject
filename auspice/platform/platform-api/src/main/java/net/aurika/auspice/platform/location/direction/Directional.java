package net.aurika.auspice.platform.location.direction;

import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.LocationProperties.VAL_PITCH;
import static net.aurika.auspice.platform.location.LocationProperties.VAL_YAW;

public interface Directional extends PitchAware, YawAware, Examinable {

  @Contract(value = "_, _ -> new", pure = true)
  @ExaminableConstructor(publicType = Directional.class, properties = {VAL_PITCH, VAL_YAW})
  static @NotNull Directional directional(float pitch, float yaw) { return new DirectionalImpl(pitch, yaw); }

  @Override
  @ExaminablePropertyGetter(VAL_PITCH)
  float pitch();

  @Override
  @ExaminablePropertyGetter(VAL_YAW)
  float yaw();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_PITCH, pitch()),
        ExaminableProperty.of(VAL_YAW, yaw())
    );
  }

}

final class DirectionalImpl implements Directional {

  private final float pitch;
  private final float yaw;

  public DirectionalImpl(float pitch, float yaw) {
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public float pitch() { return this.pitch; }

  @Override
  public float yaw() { return this.yaw; }

}
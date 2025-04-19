package net.aurika.auspice.platform.location;

public class AbstractDirectional implements Directional {

  private final float pitch;
  private final float yaw;

  public AbstractDirectional(float pitch, float yaw) {
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public float pitch() {
    return this.pitch;
  }

  @Override
  public float yaw() {
    return this.yaw;
  }

}

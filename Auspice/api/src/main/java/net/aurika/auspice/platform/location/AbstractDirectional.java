package net.aurika.auspice.platform.location;

class AbstractDirectional implements Directional {

  private final float pitch;
  private final float yaw;

  public AbstractDirectional(float pitch, float yaw) {
    this.pitch = pitch;
    this.yaw = yaw;
  }

  public float getPitch() {
    return this.pitch;
  }

  public float getYaw() {
    return this.yaw;
  }

}

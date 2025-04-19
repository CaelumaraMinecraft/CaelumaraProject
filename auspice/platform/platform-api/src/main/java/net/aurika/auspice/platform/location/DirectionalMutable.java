package net.aurika.auspice.platform.location;

public interface DirectionalMutable extends Directional, PitchMutable, YawMutable {

  @Override
  float pitch();

  @Override
  void pitch(float pitch);

  @Override
  float yaw();

  @Override
  void yaw(float yaw);

}

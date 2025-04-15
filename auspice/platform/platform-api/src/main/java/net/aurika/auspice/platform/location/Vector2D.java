package net.aurika.auspice.platform.location;

public interface Vector2D extends VectorXAware, VectorZAware {

  @Override
  double x();

  @Override
  double z();

}

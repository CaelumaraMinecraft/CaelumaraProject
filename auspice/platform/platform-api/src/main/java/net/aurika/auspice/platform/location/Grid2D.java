package net.aurika.auspice.platform.location;

public interface Grid2D extends GridXAware, GridZAware {

  @Override
  int gridX();

  @Override
  int gridZ();

}


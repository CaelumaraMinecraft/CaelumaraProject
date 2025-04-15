package net.aurika.auspice.platform.location;

public interface Block2D extends BlockXAware, BlockZAware {

  @Override
  int x();

  @Override
  int z();

}


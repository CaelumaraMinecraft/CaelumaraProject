package net.aurika.auspice.platform.location;

public interface Float3DMutable extends Float3D, FloatXMutable, FloatYMutable, FloatZMutable {

  @Override
  double floatX();

  @Override
  void floatX(double x);

  @Override
  double floatY();

  @Override
  void floatY(double y);

  @Override
  double floatZ();

  @Override
  void floatZ(double z);

}

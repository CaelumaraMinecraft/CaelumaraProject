package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Float3PosMutable extends Float3Pos, FloatXMutable, FloatYMutable, FloatZMutable {

  @Override
  double floatX();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Float3PosMutable floatX(double x);

  @Override
  double floatY();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Float3PosMutable floatY(double y);

  @Override
  double floatZ();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Float3PosMutable floatZ(double z);

}

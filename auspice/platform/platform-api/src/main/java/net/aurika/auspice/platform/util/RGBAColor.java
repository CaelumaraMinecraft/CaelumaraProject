package net.aurika.auspice.platform.util;

import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.kyori.adventure.util.ARGBLike;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.stream.Stream;

public interface RGBAColor extends ARGBLike, Examinable {

  String RED = RGBColor.VAL_RED;
  String GREEN = RGBColor.VAL_GREEN;
  String BLUE = RGBColor.VAL_BLUE;
  String ALPHA = "alpha";

  @Contract("_, _, _, _ -> new")
  @ExaminableConstructor(publicType = RGBAColor.class, properties = {RED, GREEN, BLUE, ALPHA})
  static @NotNull RGBAColor rgbaColor(int r, int g, int b, int a) {
    return new RGBAColorImpl(r, g, b, a);
  }

  @Override
  @Range(from = 0L, to = 255L)
  int red();

  @Override
  @Range(from = 0L, to = 255L)
  int green();

  @Override
  @Range(from = 0L, to = 255L)
  int blue();

  @Override
  @Range(from = 0L, to = 255L)
  int alpha();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(RED, red()),     // red
        ExaminableProperty.of(GREEN, green()), // green
        ExaminableProperty.of(BLUE, blue()),   // blue
        ExaminableProperty.of(ALPHA, alpha())  // alpha
    );
  }

}

final class RGBAColorImpl implements RGBAColor {

  private final int r, g, b, a;

  RGBAColorImpl(int r, int g, int b, int a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

  @Override
  @Range(from = 0L, to = 255L)
  public int red() { return r; }

  @Override
  @Range(from = 0L, to = 255L)
  public int green() { return g; }

  @Override
  @Range(from = 0L, to = 255L)
  public int blue() { return b; }

  @Override
  @Range(from = 0L, to = 255L)
  public int alpha() { return a; }

}

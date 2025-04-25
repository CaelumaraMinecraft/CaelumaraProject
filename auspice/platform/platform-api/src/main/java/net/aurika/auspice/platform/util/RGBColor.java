package net.aurika.auspice.platform.util;

import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.util.RGBLike;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.stream.Stream;

import static net.aurika.auspice.platform.util.RGBColorImpl.BIT_MASK;

@SuppressWarnings("PointlessBitwiseExpression")
public interface RGBColor extends RGBLike, Examinable {

  /**
   * White, or (0xFF,0xFF,0xFF) in (R,G,B)
   */
  public static final RGBColor WHITE = fromRGB(0xFFFFFF);

  /**
   * Silver, or (0xC0,0xC0,0xC0) in (R,G,B)
   */
  public static final RGBColor SILVER = fromRGB(0xC0C0C0);

  /**
   * Gray, or (0x80,0x80,0x80) in (R,G,B)
   */
  public static final RGBColor GRAY = fromRGB(0x808080);

  /**
   * Black, or (0x00,0x00,0x00) in (R,G,B)
   */
  public static final RGBColor BLACK = fromRGB(0x000000);

  /**
   * Red, or (0xFF,0x00,0x00) in (R,G,B)
   */
  public static final RGBColor RED = fromRGB(0xFF0000);

  /**
   * Maroon, or (0x80,0x00,0x00) in (R,G,B)
   */
  public static final RGBColor MAROON = fromRGB(0x800000);

  /**
   * Yellow, or (0xFF,0xFF,0x00) in (R,G,B)
   */
  public static final RGBColor YELLOW = fromRGB(0xFFFF00);

  /**
   * Olive, or (0x80,0x80,0x00) in (R,G,B)
   */
  public static final RGBColor OLIVE = fromRGB(0x808000);

  /**
   * Lime, or (0x00,0xFF,0x00) in (R,G,B)
   */
  public static final RGBColor LIME = fromRGB(0x00FF00);

  /**
   * Green, or (0x00,0x80,0x00) in (R,G,B)
   */
  public static final RGBColor GREEN = fromRGB(0x008000);

  /**
   * Aqua, or (0x00,0xFF,0xFF) in (R,G,B)
   */
  public static final RGBColor AQUA = fromRGB(0x00FFFF);

  /**
   * Teal, or (0x00,0x80,0x80) in (R,G,B)
   */
  public static final RGBColor TEAL = fromRGB(0x008080);

  /**
   * Blue, or (0x00,0x00,0xFF) in (R,G,B)
   */
  public static final RGBColor BLUE = fromRGB(0x0000FF);

  /**
   * Navy, or (0x00,0x00,0x80) in (R,G,B)
   */
  public static final RGBColor NAVY = fromRGB(0x000080);

  /**
   * Fuchsia, or (0xFF,0x00,0xFF) in (R,G,B)
   */
  public static final RGBColor FUCHSIA = fromRGB(0xFF00FF);

  /**
   * Purple, or (0x80,0x00,0x80) in (R,G,B)
   */
  public static final RGBColor PURPLE = fromRGB(0x800080);

  /**
   * Orange, or (0xFF,0xA5,0x00) in (R,G,B)
   */
  public static final RGBColor ORANGE = fromRGB(0xFFA500);

  String VAL_RED = "red";
  String VAL_GREEN = "green";
  String VAL_BLUE = "blue";

  /**
   * Creates a new color object from an integer that contains the red,
   * green, and blue bytes in the lowest order 24 bits.
   *
   * @param rgb the integer storing the red, green, and blue values
   * @return a new color object for specified values
   * @throws IllegalArgumentException if any data is in the highest order 8
   *                                  bits
   */
  public static @NotNull RGBColor fromRGB(int rgb) throws IllegalArgumentException {
    Validate.Arg.require((rgb >> 24) == 0, "rgb", "Extraneous data in: " + rgb);
    return fromRGB(rgb >> 16 & BIT_MASK, rgb >> 8 & BIT_MASK, rgb >> 0 & BIT_MASK);
  }

  @Contract("_, _, _ -> new")
  @ExaminableConstructor(publicType = RGBColor.class, properties = {VAL_RED, VAL_GREEN, VAL_BLUE})
  static @NotNull RGBColor fromRGB(int red, int green, int blue) throws IllegalArgumentException {
    return new RGBColorImpl(red, green, blue);
  }

  /**
   * Creates a new color object from an integer that contains the blue,
   * green, and red bytes in the lowest order 24 bits.
   *
   * @param bgr the integer storing the blue, green, and red values
   * @return a new color object for specified values
   * @throws IllegalArgumentException if any data is in the highest order 8
   *                                  bits
   */
  public static @NotNull RGBColor fromBGR(int bgr) throws IllegalArgumentException {
    Validate.Arg.require((bgr >> 24) == 0, "bgr", "Extraneous data in: " + bgr);
    return fromBGR(bgr >> 16 & BIT_MASK, bgr >> 8 & BIT_MASK, bgr >> 0 & BIT_MASK);
  }

  @Contract("_, _, _ -> new")
  @ExaminableConstructor(publicType = RGBColor.class, properties = {VAL_BLUE, VAL_GREEN, VAL_RED})
  static @NotNull RGBColor fromBGR(int blue, int green, int red) throws IllegalArgumentException {
    return new RGBColorImpl(red, green, blue);
  }

  @Override
  @Range(from = 0L, to = 255L)
  @ExaminablePropertyGetter(value = VAL_RED)
  int red();

  @Override
  @Range(from = 0L, to = 255L)
  @ExaminablePropertyGetter(value = VAL_GREEN)
  int green();

  @Override
  @Range(from = 0L, to = 255L)
  @ExaminablePropertyGetter(value = VAL_BLUE)
  int blue();

  @Override
  default @NotNull Stream<? extends @NotNull ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_RED, red()),     //
        ExaminableProperty.of(VAL_GREEN, green()), //
        ExaminableProperty.of(VAL_BLUE, blue())    //
    );
  }

}

final class RGBColorImpl implements RGBColor {

  static final int BIT_MASK = 0xff;

  private final int r, g, b;

  RGBColorImpl(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  @Range(from = 0L, to = 255L)
  public int red() {
    return r;
  }

  @Override
  @Range(from = 0L, to = 255L)
  public int green() {
    return g;
  }

  @Override
  @Range(from = 0L, to = 255L)
  public int blue() {
    return b;
  }

}

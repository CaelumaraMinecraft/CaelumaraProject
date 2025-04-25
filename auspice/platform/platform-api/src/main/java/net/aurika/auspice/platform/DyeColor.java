package net.aurika.auspice.platform;

import net.aurika.auspice.platform.util.RGBColor;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * All supported color values for dyes and cloth
 */
public enum DyeColor {

  /**
   * Represents white dye.
   */
  WHITE(0x0, 0xF, RGBColor.fromRGB(0xF9FFFE), RGBColor.fromRGB(0xF0F0F0)),
  /**
   * Represents orange dye.
   */
  ORANGE(0x1, 0xE, RGBColor.fromRGB(0xF9801D), RGBColor.fromRGB(0xEB8844)),
  /**
   * Represents magenta dye.
   */
  MAGENTA(0x2, 0xD, RGBColor.fromRGB(0xC74EBD), RGBColor.fromRGB(0xC354CD)),
  /**
   * Represents light blue dye.
   */
  LIGHT_BLUE(0x3, 0xC, RGBColor.fromRGB(0x3AB3DA), RGBColor.fromRGB(0x6689D3)),
  /**
   * Represents yellow dye.
   */
  YELLOW(0x4, 0xB, RGBColor.fromRGB(0xFED83D), RGBColor.fromRGB(0xDECF2A)),
  /**
   * Represents lime dye.
   */
  LIME(0x5, 0xA, RGBColor.fromRGB(0x80C71F), RGBColor.fromRGB(0x41CD34)),
  /**
   * Represents pink dye.
   */
  PINK(0x6, 0x9, RGBColor.fromRGB(0xF38BAA), RGBColor.fromRGB(0xD88198)),
  /**
   * Represents gray dye.
   */
  GRAY(0x7, 0x8, RGBColor.fromRGB(0x474F52), RGBColor.fromRGB(0x434343)),
  /**
   * Represents light gray dye.
   */
  LIGHT_GRAY(0x8, 0x7, RGBColor.fromRGB(0x9D9D97), RGBColor.fromRGB(0xABABAB)),
  /**
   * Represents cyan dye.
   */
  CYAN(0x9, 0x6, RGBColor.fromRGB(0x169C9C), RGBColor.fromRGB(0x287697)),
  /**
   * Represents purple dye.
   */
  PURPLE(0xA, 0x5, RGBColor.fromRGB(0x8932B8), RGBColor.fromRGB(0x7B2FBE)),
  /**
   * Represents blue dye.
   */
  BLUE(0xB, 0x4, RGBColor.fromRGB(0x3C44AA), RGBColor.fromRGB(0x253192)),
  /**
   * Represents brown dye.
   */
  BROWN(0xC, 0x3, RGBColor.fromRGB(0x835432), RGBColor.fromRGB(0x51301A)),
  /**
   * Represents green dye.
   */
  GREEN(0xD, 0x2, RGBColor.fromRGB(0x5E7C16), RGBColor.fromRGB(0x3B511A)),
  /**
   * Represents red dye.
   */
  RED(0xE, 0x1, RGBColor.fromRGB(0xB02E26), RGBColor.fromRGB(0xB3312C)),
  /**
   * Represents black dye.
   */
  BLACK(0xF, 0x0, RGBColor.fromRGB(0x1D1D21), RGBColor.fromRGB(0x1E1B1B));

  private final static DyeColor[] BY_WOOL_DATA;
  private final static DyeColor[] BY_DYE_DATA;
  private final static Map<RGBColor, DyeColor> BY_COLOR;
  private final static Map<RGBColor, DyeColor> BY_FIREWORK;

  private final byte woolData;
  private final byte dyeData;
  private final RGBColor color;
  private final RGBColor firework;

  private DyeColor(final int woolData, final int dyeData, RGBColor color, RGBColor firework) {
    this.woolData = (byte) woolData;
    this.dyeData = (byte) dyeData;
    this.color = color;
    this.firework = firework;
  }

  /**
   * Gets the associated wool data value representing this RGBColor.
   *
   * @return A byte containing the wool data value of this RGBColor
   * @see #getDyeData()
   * @deprecated Magic value
   */
  @Deprecated
  public byte getWoolData() {
    return woolData;
  }

  /**
   * Gets the associated dye data value representing this RGBColor.
   *
   * @return A byte containing the dye data value of this RGBColor
   * @see #getWoolData()
   * @deprecated Magic value
   */
  @Deprecated
  public byte getDyeData() {
    return dyeData;
  }

  /**
   * Gets the color that this dye represents.
   *
   * @return The {@link RGBColor} that this dye represents
   */
  public RGBColor color() {
    return color;
  }

  /**
   * Gets the firework color that this dye represents.
   *
   * @return The {@link RGBColor} that this dye represents
   */
  public RGBColor fireworkColor() {
    return firework;
  }

  /**
   * Gets the DyeRGBColor with the given wool data value.
   *
   * @param data Wool data value to fetch
   * @return The {@link DyeColor} representing the given value, or null if
   * it doesn't exist
   * @see #getByDyeData(byte)
   * @deprecated Magic value
   */
  @Deprecated
  public static @Nullable DyeColor getByWoolData(final byte data) {
    int i = 0xff & data;
    if (i >= BY_WOOL_DATA.length) {
      return null;
    }
    return BY_WOOL_DATA[i];
  }

  /**
   * Gets the DyeRGBColor with the given dye data value.
   *
   * @param data Dye data value to fetch
   * @return The {@link DyeColor} representing the given value, or null if
   * it doesn't exist
   * @see #getByWoolData(byte)
   * @deprecated Magic value
   */
  @Deprecated
  public static DyeColor getByDyeData(final byte data) {
    int i = 0xff & data;
    if (i >= BY_DYE_DATA.length) {
      return null;
    }
    return BY_DYE_DATA[i];
  }

  /**
   * Gets the DyeRGBColor with the given RGBColor value.
   *
   * @param RGBColor RGBColor value to get the dye by
   * @return The {@link DyeColor} representing the given value, or null if
   * it doesn't exist
   */
  public static DyeColor getByRGBColor(final RGBColor RGBColor) {
    return BY_COLOR.get(RGBColor);
  }

  /**
   * Gets the DyeRGBColor with the given firework RGBColor value.
   *
   * @param RGBColor RGBColor value to get dye by
   * @return The {@link DyeColor} representing the given value, or null if
   * it doesn't exist
   */
  public static DyeColor getByFireworkRGBColor(final RGBColor RGBColor) {
    return BY_FIREWORK.get(RGBColor);
  }

  /**
   * Gets the DyeRGBColor for the given name, possibly doing legacy transformations.
   *
   * @param name dye name
   * @return dye RGBColor
   * @deprecated legacy uses only
   */
  @Deprecated
  public static DyeColor legacyValueOf(String name) {
    return "SILVER".equals(name) ? DyeColor.LIGHT_GRAY : DyeColor.valueOf(name);
  }

  static {
    BY_WOOL_DATA = values();
    BY_DYE_DATA = values();
    Map<RGBColor, DyeColor> byColor = new HashMap<>();
    Map<RGBColor, DyeColor> byFirework = new HashMap<>();


    for (DyeColor dyeColor : values()) {
      BY_WOOL_DATA[dyeColor.woolData & 0xff] = dyeColor;
      BY_DYE_DATA[dyeColor.dyeData & 0xff] = dyeColor;
      byColor.put(dyeColor.color(), dyeColor);
      byFirework.put(dyeColor.fireworkColor(), dyeColor);
    }

    BY_COLOR = Collections.unmodifiableMap(byColor);
    BY_FIREWORK = Collections.unmodifiableMap(byFirework);
  }
}

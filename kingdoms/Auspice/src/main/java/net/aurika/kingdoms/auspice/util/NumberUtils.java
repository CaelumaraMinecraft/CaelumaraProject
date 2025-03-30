package net.aurika.kingdoms.auspice.util;

import org.jetbrains.annotations.Nullable;

@Deprecated
public class NumberUtils {

  public static @Nullable Long parseLong(Object o) {
    if (o instanceof Number) {
      return ((Number) o).longValue();
    }
    if (o instanceof String) {
      return Long.parseLong((String) o);
    }

    return null;
  }

  public static @Nullable Double parseDouble(Object o) {
    if (o instanceof Number) {
      return ((Number) o).doubleValue();
    }
    if (o instanceof String) {
      return Double.parseDouble((String) o);
    }
    return null;
  }

  public static @Nullable Integer parseInt(Object o) {
    if (o instanceof Number) {
      return ((Number) o).intValue();
    }
    if (o instanceof String) {
      return Integer.parseInt((String) o);
    }
    return null;
  }

}

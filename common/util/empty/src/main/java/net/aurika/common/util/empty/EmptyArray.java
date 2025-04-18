package net.aurika.common.util.empty;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public final class EmptyArray {

  private static final Map<Class<?>, Object[]> EMPTY_ARRAYS = new HashMap<>();

  @SuppressWarnings("unchecked")
  public static <T> T[] emptyArray(@NotNull Class<T> type) {
    Validate.Arg.notNull(type, "type");
    T[] emptyArray = (T[]) EMPTY_ARRAYS.get(type);
    if (emptyArray == null) {
      emptyArray = (T[]) Array.newInstance(type, 0);
      EMPTY_ARRAYS.put(type, emptyArray);
    }
    return emptyArray;
  }

}

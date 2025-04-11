package net.aurika.common.event;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;

final class Util {

  @SuppressWarnings("unchecked")
  public static <T> Class<? super T> @NotNull [] getSupers(@NotNull Class<T> clazz) {
    @Nullable Class<? super T> extended = clazz.getSuperclass();
    @NotNull Class<? super T> @NotNull [] interfaces = (Class<? super T>[]) clazz.getInterfaces();
    if (extended == null) {
      return interfaces;
    } else {
      return merge(new Class[]{extended}, interfaces);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T @NotNull [] merge(T[] @NotNull ... arrays) {
    Validate.Arg.notNull(arrays, "arrays");
    Class<? extends T> arrComponentType = (Class<T>) arrays.getClass().getComponentType().getComponentType();
    if (arrays.length == 0) {
      return (T[]) Array.newInstance(arrComponentType, 0);
    }
    if (arrays.length == 1) {
      return arrays[0];
    }
    int totalLength = 0;
    for (T[] array : arrays) {
      totalLength += array.length;
    }
    T[] merged = (T[]) Array.newInstance(arrComponentType, totalLength);
    int i = 0;
    for (T[] array : arrays) {
      for (T t : array) {
        merged[i++] = t;
      }
    }
    return merged;
  }

}

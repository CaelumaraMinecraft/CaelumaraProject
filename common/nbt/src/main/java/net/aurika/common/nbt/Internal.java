package net.aurika.common.nbt;

import java.util.Arrays;

final class Internal {

  @SuppressWarnings("unchecked")
  static <T> T cast(Object obj) {
    return (T) obj;
  }

  static int arrayHashCode(Object arr) {
    if (arr == null) {
      return 0;
    }
    if (arr instanceof boolean[]) {
      return Arrays.hashCode((boolean[]) arr);
    }
    if (arr instanceof char[]) {
      return Arrays.hashCode((char[]) arr);
    }
    if (arr instanceof byte[]) {
      return Arrays.hashCode((byte[]) arr);
    }
    if (arr instanceof short[]) {
      return Arrays.hashCode((short[]) arr);
    }
    if (arr instanceof int[]) {
      return Arrays.hashCode((int[]) arr);
    }
    if (arr instanceof long[]) {
      return Arrays.hashCode((long[]) arr);
    }
    if (arr instanceof float[]) {
      return Arrays.hashCode((float[]) arr);
    }
    if (arr instanceof double[]) {
      return Arrays.hashCode((double[]) arr);
    }
    if (arr instanceof Object[]) {
      return Arrays.hashCode((Object[]) arr);
    }
    throw new UnsupportedOperationException("Unsupported type: " + arr.getClass().getName());
  }

  static String arrayToString(Object arr) {
    if (arr == null) {
      return "null";
    }
    if (arr instanceof boolean[]) {
      return Arrays.toString((boolean[]) arr);
    }
    if (arr instanceof char[]) {
      return Arrays.toString((char[]) arr);
    }
    if (arr instanceof byte[]) {
      return Arrays.toString((byte[]) arr);
    }
    if (arr instanceof short[]) {
      return Arrays.toString((short[]) arr);
    }
    if (arr instanceof int[]) {
      return Arrays.toString((int[]) arr);
    }
    if (arr instanceof long[]) {
      return Arrays.toString((long[]) arr);
    }
    if (arr instanceof float[]) {
      return Arrays.toString((float[]) arr);
    }
    if (arr instanceof double[]) {
      return Arrays.toString((double[]) arr);
    }
    if (arr instanceof Object[]) {
      return Arrays.toString((Object[]) arr);
    }
    throw new UnsupportedOperationException("Unsupported type: " + arr.getClass().getName());
  }

}

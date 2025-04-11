package net.aurika.common.data.provider;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public enum SupportedDataTypes {

  BOOLEAN(boolean.class),
  CHAR(char.class),
  BYTE(byte.class),
  SHORT(short.class),
  INT(int.class),
  LONG(long.class),
  FLOAT(float.class),
  DOUBLE(double.class),
  STRING(String.class),
  ENUM(Enum.class),
  COLLECTION(Collection.class),
  MAP(Map.class),

  ;
  private final @NotNull Class<?> type;

  SupportedDataTypes(@NotNull Class<?> type) {
    this.type = type;
  }

  public @NotNull Class<?> type() {
    return this.type;
  }
}

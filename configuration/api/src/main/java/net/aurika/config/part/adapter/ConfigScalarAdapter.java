package net.aurika.config.part.adapter;

import net.aurika.config.part.ConfigScalar;
import org.jetbrains.annotations.NotNull;

public interface ConfigScalarAdapter<S extends ConfigScalar> extends ConfigPartAdapter<S> {

  // get adapters
  boolean isNull(@NotNull S configScalar);

  boolean getBoolean(@NotNull S configScalar);

  char getChar(@NotNull S configScalar);

  byte getByte(@NotNull S configScalar);

  short getShort(@NotNull S configScalar);

  int getInt(@NotNull S configScalar);

  long getLong(@NotNull S configScalar);

  float getFloat(@NotNull S configScalar);

  double getDouble(@NotNull S configScalar);

  @NotNull Number getNumber(@NotNull S configScalar);

  @NotNull String getString(@NotNull S configScalar);

  // set adapters
  void setNull(@NotNull S configScalar);

  void setBoolean(@NotNull S configScalar, boolean value);

  void setChar(@NotNull S configScalar, char value);

  void setByte(@NotNull S configScalar, byte value);

  void setShort(@NotNull S configScalar, short value);

  void setInt(@NotNull S configScalar, int value);

  void setLong(@NotNull S configScalar, long value);

  void setFloat(@NotNull S configScalar, float value);

  void setDouble(@NotNull S configScalar, double value);

  void setNumber(@NotNull S configScalar, @NotNull Number value);

  void setString(@NotNull S configScalar, @NotNull String value);

}

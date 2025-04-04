package net.aurika.configuration.part.accessor;

import net.aurika.configuration.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigReader extends ConfigAccessor {

  @Override
  @NotNull ConfigEntry path();

  @Override
  @NotNull ConfigReader gotoSub(@NotNull String name);

  boolean readBoolean() throws ConfigAccessException;

  byte readByte() throws ConfigAccessException;

  short readShort() throws ConfigAccessException;

  int readInt() throws ConfigAccessException;

  long readLong() throws ConfigAccessException;

  float readFloat() throws ConfigAccessException;

  double readDouble() throws ConfigAccessException;

  @NotNull String readString() throws ConfigAccessException;

}

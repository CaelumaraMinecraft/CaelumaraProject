package net.aurika.configuration.part.accessor;

import net.aurika.configuration.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigWriter extends ConfigAccessor {

  @Override
  @NotNull ConfigEntry path();

  @Override
  @NotNull ConfigAccessor gotoSub(@NotNull String name);

  void writeBoolean(boolean value) throws ConfigAccessException;

  void writeByte(byte value) throws ConfigAccessException;

  void writeShort(short value) throws ConfigAccessException;

  void writeInt(int value) throws ConfigAccessException;

  void writeLong(long value) throws ConfigAccessException;

  void writeFloat(float value) throws ConfigAccessException;

  void writeDouble(double value) throws ConfigAccessException;

  void writeString(@NotNull String value) throws ConfigAccessException;

}

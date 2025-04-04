package net.aurika.configuration.part.accessor;

import net.aurika.configuration.part.ConfigPart;
import net.aurika.configuration.part.visitor.ConfigVisitor;
import net.aurika.configuration.path.ConfigEntry;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractConfigAccessor<P extends ConfigPart> implements DefaultableConfigAccessor, ConfigReader, ConfigWriter {

  protected final @NotNull ConfigEntry path;
  protected final @NotNull P actualConfig;
  protected final @NotNull P defaultConfig;
  protected boolean useDefault = true;

  public AbstractConfigAccessor(
      @NotNull ConfigEntry path,
      @NotNull P actualConfig,
      @NotNull P defaultConfig
  ) {
    Validate.Arg.notNull(path, "path");
    Validate.Arg.notNull(actualConfig, "actualConfig");
    Validate.Arg.notNull(defaultConfig, "defaultConfig");
    this.path = path;
    this.actualConfig = actualConfig;
    this.defaultConfig = defaultConfig;
  }

  @Override
  public @NotNull ConfigEntry path() {
    return path;
  }

  @Override
  public abstract @NotNull AbstractConfigAccessor<P> gotoSub(@NotNull String name);

  @Override
  @SuppressWarnings("unchecked")
  public @NotNull P getConfig() throws ConfigAccessException {
    if (ConfigVisitor.hasConfig(actualConfig, path)) {
      return (P) ConfigVisitor.get(actualConfig, path);
    } else {
      return (P) ConfigVisitor.get(defaultConfig, path);
    }
  }

  public boolean isUsingDefault() {
    return useDefault;
  }

  @Override
  public @NotNull AbstractConfigAccessor<P> noDefault() {
    useDefault = false;
    return this;
  }

  @Override
  public @NotNull AbstractConfigAccessor<P> useDefault() {
    useDefault = true;
    return this;
  }

  @Override
  public abstract boolean readBoolean() throws ConfigAccessException;

  @Override
  public abstract byte readByte() throws ConfigAccessException;

  @Override
  public abstract short readShort() throws ConfigAccessException;

  @Override
  public abstract int readInt() throws ConfigAccessException;

  @Override
  public abstract long readLong() throws ConfigAccessException;

  @Override
  public abstract float readFloat() throws ConfigAccessException;

  @Override
  public abstract double readDouble() throws ConfigAccessException;

  @Override
  public abstract @NotNull String readString() throws ConfigAccessException;

  @Override
  public abstract void writeBoolean(boolean value) throws ConfigAccessException;

  @Override
  public abstract void writeByte(byte value) throws ConfigAccessException;

  @Override
  public abstract void writeShort(short value) throws ConfigAccessException;

  @Override
  public abstract void writeInt(int value) throws ConfigAccessException;

  @Override
  public abstract void writeLong(long value) throws ConfigAccessException;

  @Override
  public abstract void writeFloat(float value) throws ConfigAccessException;

  @Override
  public abstract void writeDouble(double value) throws ConfigAccessException;

  @Override
  public abstract void writeString(@NotNull String value) throws ConfigAccessException;

}

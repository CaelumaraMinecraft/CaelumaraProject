package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public abstract class DataPart implements BaseDataPart {

  public final boolean getBoolean() throws ClassCastException {
    return ((DataBoolean) this).value();
  }

  public final char getChar() throws ClassCastException {
    return ((DataChar) this).value();
  }

  public final byte getByte() throws ClassCastException {
    return ((DataByte) this).value();
  }

  public final short getShort() throws ClassCastException {
    return ((DataShort) this).value();
  }

  public final int getInt() throws ClassCastException {
    return ((DataInt) this).value();
  }

  public final long getLong() throws ClassCastException {
    return ((DataLong) this).value();
  }

  public final float getFloat() throws ClassCastException {
    return ((DataFloat) this).value();
  }

  public final double getDouble() throws ClassCastException {
    return ((DataDouble) this).value();
  }

  public final @NotNull String getString() throws ClassCastException {
    return ((DataString) this).valueObject();
  }

  public abstract @NotNull Object valueObject();

  public abstract @NotNull DataType type();

}

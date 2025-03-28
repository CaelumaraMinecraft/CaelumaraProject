package net.aurika.property;

public interface ByteProperty extends BaseProperty {

  @Override
  String name();

  @Override
  boolean isSet();

  public byte get();

  public void set(byte value);

}

class BytePropertyImpl extends BasePropertyImpl implements ByteProperty {

  private byte value;

  BytePropertyImpl(String name) {
    this(name, false, (byte) 0);
  }

  BytePropertyImpl(String name, byte value) {
    this(name, true, value);
  }

  private BytePropertyImpl(String name, boolean set, byte value) {
    super(name, set);
    this.value = value;
  }

  @Override
  public byte get() throws PropertyNotInitializedException {
    if (set) {
      return value;
    } else {
      throw new PropertyNotInitializedException(name);
    }
  }

  @Override
  public void set(byte value) {
    this.value = value;
    this.set = true;
  }

}

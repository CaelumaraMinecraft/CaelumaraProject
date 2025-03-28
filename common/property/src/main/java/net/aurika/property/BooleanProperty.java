package net.aurika.property;

public interface BooleanProperty extends BaseProperty {

  @Override
  String name();

  @Override
  boolean isSet();

  boolean get() throws PropertyNotInitializedException;

  void set(boolean value);

}

class BooleanPropertyImpl extends BasePropertyImpl implements BooleanProperty {

  private boolean value;

  BooleanPropertyImpl(String name) {
    this(name, false, false);
  }

  BooleanPropertyImpl(String name, boolean value) {
    this(name, true, value);
  }

  private BooleanPropertyImpl(String name, boolean set, boolean value) {
    super(name, set);
    this.value = value;
  }

  @Override
  public boolean get() throws PropertyNotInitializedException {
    if (set) {
      return value;
    } else {
      throw new PropertyNotInitializedException(name);
    }
  }

  @Override
  public void set(boolean value) {
    this.value = value;
    this.set = true;
  }

}

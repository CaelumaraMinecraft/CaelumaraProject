package net.aurika.property;

public interface FloatProperty extends BaseProperty {

  @Override
  String name();

  @Override
  boolean isSet();

  float get() throws PropertyNotInitializedException;

  void set(float value);

}

class FloatPropertyImpl extends BasePropertyImpl implements FloatProperty {

  private float value;

  FloatPropertyImpl(String name) {
    this(name, false, 0.0F);
  }

  FloatPropertyImpl(String name, float value) {
    this(name, true, value);
  }

  private FloatPropertyImpl(String name, boolean set, float value) {
    super(name, set);
    this.value = value;
  }

  @Override
  public float get() throws PropertyNotInitializedException {
    if (set) {
      return value;
    } else {
      throw new PropertyNotInitializedException(name);
    }
  }

  @Override
  public void set(float value) {
    this.value = value;
    this.set = true;
  }

}

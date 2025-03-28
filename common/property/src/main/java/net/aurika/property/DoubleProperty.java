package net.aurika.property;

public interface DoubleProperty extends BaseProperty {

  @Override
  String name();

  @Override
  boolean isSet();

  double get() throws PropertyNotInitializedException;

  void set(double value);

}

class DoublePropertyImpl extends BasePropertyImpl implements DoubleProperty {

  private double value;

  DoublePropertyImpl(String name) {
    this(name, false, 0.0);
  }

  DoublePropertyImpl(String name, double value) {
    this(name, true, value);
  }

  private DoublePropertyImpl(String name, boolean set, double value) {
    super(name, set);
    this.value = value;
  }

  @Override
  public double get() throws PropertyNotInitializedException {
    if (set) {
      return value;
    } else {
      throw new PropertyNotInitializedException(name);
    }
  }

  @Override
  public void set(double value) {
    this.value = value;
    this.set = true;
  }

}

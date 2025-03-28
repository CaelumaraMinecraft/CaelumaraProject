package net.aurika.property;

import org.jetbrains.annotations.NotNull;

public interface Property<T> extends BaseProperty {

  /**
   * Create a not initialized property.
   */
  static <T> @NotNull Property<T> property(@NotNull String name) {
    return new PropertyImpl<>(name);
  }

  /**
   * Create an initialized property.
   */
  static <T> @NotNull Property<T> property(@NotNull String name, T value) {
    return new PropertyImpl<>(name, value);
  }

  /**
   * Create a not initialized boolean property.
   */
  static @NotNull BooleanProperty booleanProperty(@NotNull String name) {
    return new BooleanPropertyImpl(name);
  }

  /**
   * Create an initialized boolean property.
   */
  static @NotNull BooleanProperty booleanProperty(@NotNull String name, boolean value) {
    return new BooleanPropertyImpl(name, value);
  }

  /**
   * Create a not initialized char property.
   */
  static @NotNull CharProperty charProperty(@NotNull String name) {
    return new CharPropertyImpl(name);
  }

  /**
   * Create an initialized char property.
   */
  static @NotNull CharProperty charProperty(@NotNull String name, char value) {
    return new CharPropertyImpl(name, value);
  }

  /**
   * Create a not initialized byte property.
   */
  static @NotNull ByteProperty byteProperty(@NotNull String name) {
    return new BytePropertyImpl(name);
  }

  /**
   * Create an initialized byte property.
   */
  static @NotNull ByteProperty byteProperty(@NotNull String name, byte value) {
    return new BytePropertyImpl(name, value);
  }

  /**
   * Create a not initialized short property.
   */
  static @NotNull ShortProperty shortProperty(@NotNull String name) {
    return new ShortPropertyImpl(name);
  }

  /**
   * Create an initialized short property.
   */
  static @NotNull ShortProperty shortProperty(@NotNull String name, short value) {
    return new ShortPropertyImpl(name, value);
  }

  /**
   * Create a not initialized int property.
   */
  static @NotNull IntProperty intProperty(@NotNull String name) {
    return new IntPropertyImpl(name);
  }

  /**
   * Create an initialized int property.
   */
  static @NotNull IntProperty intProperty(@NotNull String name, int value) {
    return new IntPropertyImpl(name, value);
  }

  /**
   * Create a not initialized long property.
   */
  static @NotNull LongProperty longProperty(@NotNull String name) {
    return new LongPropertyImpl(name);
  }

  /**
   * Create an initialized long property.
   */
  static @NotNull LongProperty longProperty(@NotNull String name, long value) {
    return new LongPropertyImpl(name, value);
  }

  /**
   * Create a not initialized float property.
   */
  static @NotNull FloatProperty floatProperty(@NotNull String name) {
    return new FloatPropertyImpl(name);
  }

  /**
   * Create an initialized float property.
   */
  static @NotNull FloatProperty floatProperty(@NotNull String name, float value) {
    return new FloatPropertyImpl(name, value);
  }

  /**
   * Create a not initialized long property.
   */
  static @NotNull DoubleProperty doubleProperty(@NotNull String name) {
    return new DoublePropertyImpl(name);
  }

  /**
   * Create an initialized long property.
   */
  static @NotNull DoubleProperty doubleProperty(@NotNull String name, double value) {
    return new DoublePropertyImpl(name, value);
  }

  @Override
  String name();

  @Override
  boolean isSet();

  T get() throws PropertyNotInitializedException;

  void set(T value);

}

class PropertyImpl<T> extends BasePropertyImpl implements Property<T> {

  private T value;

  PropertyImpl(String name) {
    this(name, false, null);
  }

  PropertyImpl(String name, T value) {
    this(name, true, value);
  }

  private PropertyImpl(String name, boolean set, T value) {
    super(name, set);
    this.value = value;
  }

  @Override
  public T get() throws PropertyNotInitializedException {
    if (set) {
      return value;
    } else {
      throw new PropertyNotInitializedException(name);
    }
  }

  @Override
  public void set(T value) {
    this.value = value;
    this.set = true;
  }

}

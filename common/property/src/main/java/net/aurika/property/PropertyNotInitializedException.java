package net.aurika.property;

public class PropertyNotInitializedException extends RuntimeException {

  protected final String propertyName;

  protected PropertyNotInitializedException(String propertyName) {
    super("Property '" + propertyName + "' not initialized");
    this.propertyName = propertyName;
  }

}

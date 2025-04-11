package net.aurika.dyanasis.api.declaration.property.container;

import net.aurika.dyanasis.api.declaration.member.property.DyanasisProperty;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleDyanasisPropertyRegistry<P extends DyanasisProperty> implements DyanasisPropertyContainer<P> {

  protected final @NotNull Map<String, P> properties;

  public SimpleDyanasisPropertyRegistry() {
    this(new HashMap<>());
  }

  public SimpleDyanasisPropertyRegistry(@NotNull Map<String, P> properties) {
    Validate.Arg.notNull(properties, "properties");
    this.properties = properties;
  }

  /**
   * Adds a dyanasis property to this {@linkplain SimpleDyanasisPropertyRegistry} and
   * returns the old property that contains the same name to {@code property}.
   *
   * @param property the property to add
   * @return the old property
   */
  public @Nullable DyanasisProperty addProperty(@NotNull P property) {
    Validate.Arg.notNull(property, "property");
    String name = property.name();
    Objects.requireNonNull(name, "property.name()");
    return properties.put(name, property);
  }

  @Override
  public boolean hasProperty(@NotNull String name) {
    return properties.containsKey(name);
  }

  @Override
  public @Nullable P getProperty(@NotNull String name) {
    return properties.get(name);
  }

  @Override
  public @Unmodifiable @NotNull Map<String, P> allProperties() {
    return Collections.unmodifiableMap(properties);
  }

}

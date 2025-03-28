package net.aurika.dyanasis.api.declaration.invokable.property.container;

import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public interface DyanasisPropertyContainer<P extends DyanasisProperty> {

  /**
   * Gets the {@linkplain DyanasisPropertyContainer} that has none property.
   *
   * @return the empty properties
   */
  static @NotNull EmptyDyanasisPropertyContainer empty() {
    return EmptyDyanasisPropertyContainer.INSTANCE;
  }

  /**
   * Returns {@code true} if it has the property that equals the {@code name}.
   */
  boolean hasProperty(@NotNull String name);

  /**
   * Gets a property by the {@code name}.
   */
  @Nullable P getProperty(@NotNull String name);

  /**
   * Gets all available properties.
   *
   * @return the properties
   */
  @Unmodifiable
  @NotNull Map<String, ? extends P> allProperties();

}

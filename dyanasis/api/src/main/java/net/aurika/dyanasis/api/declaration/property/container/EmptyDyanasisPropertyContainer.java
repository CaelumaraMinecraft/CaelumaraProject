package net.aurika.dyanasis.api.declaration.property.container;

import net.aurika.dyanasis.api.declaration.member.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;

public final class EmptyDyanasisPropertyContainer<P extends DyanasisProperty> implements DyanasisPropertyContainer<P> {

  public static final EmptyDyanasisPropertyContainer<?> INSTANCE = new EmptyDyanasisPropertyContainer<>();

  private EmptyDyanasisPropertyContainer() {
  }

  @Override
  public boolean hasProperty(@NotNull String name) {
    return false;
  }

  @Override
  public @Nullable P getProperty(@NotNull String name) {
    return null;
  }

  @Override
  public @Unmodifiable @NotNull Map<String, ? extends P> allProperties() {
    return Collections.emptyMap();
  }

}

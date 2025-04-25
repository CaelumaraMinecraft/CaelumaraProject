package net.aurika.auspice.platform.adapter;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DefaultAdapterRegistry implements AdapterRegistry {

  private final @NotNull Map<Class<?>, Adapter<?, ?>> byAuspice;
  private final @NotNull Map<Class<?>, Adapter<?, ?>> byPlatform;

  public DefaultAdapterRegistry() {
    this(new HashMap<>(), new HashMap<>());
  }

  public DefaultAdapterRegistry(@NotNull Map<Class<?>, Adapter<?, ?>> byAuspice, @NotNull Map<Class<?>, Adapter<?, ?>> byPlatform) {
    Validate.Arg.notNull(byAuspice, "byAuspice");
    Validate.Arg.notNull(byPlatform, "byPlatform");
    this.byAuspice = byAuspice;
    this.byPlatform = byPlatform;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public @NotNull <AT, PT> Adapter<AT, PT> getByAuspice(@NotNull Class<AT> auspiceType) {
    @Nullable Adapter byAuspice = this.byAuspice.get(auspiceType);
    if (byAuspice == null) {
      throw new IllegalStateException("No adapter found for type " + auspiceType);
    }
    if (byAuspice.auspiceType() != auspiceType) {
      throw new IllegalStateException("Error adapter found for type " + auspiceType);
    }
    return (Adapter<AT, PT>) byAuspice;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public @NotNull <AT, PT> Adapter<AT, PT> getByPlatform(@NotNull Class<PT> platformType) {
    @Nullable Adapter byPlatform = this.byPlatform.get(platformType);
    if (byPlatform == null) {
      throw new IllegalStateException("No adapter found for type " + platformType);
    }
    if (!platformType.isAssignableFrom(byPlatform.platformType())) {
      throw new IllegalStateException("Error adapter found for type " + platformType);
    }
    return (Adapter<AT, PT>) byPlatform;
  }

}

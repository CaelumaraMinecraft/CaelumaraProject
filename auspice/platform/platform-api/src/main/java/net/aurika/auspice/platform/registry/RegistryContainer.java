package net.aurika.auspice.platform.registry;

import net.aurika.common.annotation.container.ThrowOnAbsent;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public interface RegistryContainer extends Iterable<Registry<?>> {

  /**
   * Returns if it has a registry that keyed by the {@code key}.
   *
   * @param key the registry key
   * @return has the registry
   */
  boolean hasRegistry(@NotNull RegistryKey<?> key);

  @ThrowOnAbsent
  @NotNull <T extends Keyed> Registry<T> getRegistry(@NotNull RegistryKey<T> key) throws NoSuchRegistryException;

  @Nullable <T extends Keyed> Registry<T> findRegistry(@NotNull RegistryKey<T> key);

  @Override
  @NotNull Iterator<Registry<?>> iterator();

}

package net.aurika.auspice.platform.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * Represents a registry of Minecraft objects that may be retrieved by a {@link Key}.
 *
 * @param <T> type of item in the registry
 */
public interface Registry<T extends Keyed> extends net.aurika.common.keyed.Keyed<RegistryKey<T>>, Iterable<T> {

  @Override
  @NotNull RegistryKey<T> key();

  @Override
  @NotNull Iterator<T> iterator();

  /**
   * Gets the registry item type.
   *
   * @return the item type
   */
  @NotNull Class<? extends T> itemType();

}

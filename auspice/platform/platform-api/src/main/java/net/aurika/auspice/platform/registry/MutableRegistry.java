package net.aurika.auspice.platform.registry;

import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface MutableRegistry<T extends Keyed> extends Registry<T> {

  /**
   * Adds an item to the registry.
   *
   * @param item the item
   * @throws RegisterExistingException when registering an existing key that an item keyed
   */
  void add(@NotNull T item) throws RegisterExistingException, IllegalArgumentException;

}

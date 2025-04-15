package net.aurika.auspice.platform.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

/**
 * Represents a registry of Minecraft objects that may be retrieved by a {@link Key}.
 *
 * @param <T> type of item in the registry
 */
public interface Registry<T extends Keyed> extends Iterable<T> {
}

package net.aurika.auspice.platform.registry;

import net.kyori.adventure.key.Key;

/**
 * Represents a registry of Minecraft objects that may be retrieved by{@link Key}.
 *
 * @param <T> type of item in the registry
 */
public interface Registry<T extends net.kyori.adventure.key.Keyed> extends Iterable<T> {
}

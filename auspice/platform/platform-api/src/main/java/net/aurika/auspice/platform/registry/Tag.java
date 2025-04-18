package net.aurika.auspice.platform.registry;

import org.jetbrains.annotations.NotNull;

/**
 * A tag to a registry.
 *
 * @param <R> the registry type
 */
public interface Tag<R extends Registry<?>> {

  @NotNull Class<? extends R> registryType();

}

package net.aurika.dyanasis.api.runtime;

import org.jetbrains.annotations.NotNull;

/**
 * A dyanasis runtime.
 */
public interface DyanasisRuntime {
    /**
     * Gets the runtime environment.
     *
     * @return the runtime environment
     */
    @NotNull DyanasisRuntimeEnvironment environment();
}

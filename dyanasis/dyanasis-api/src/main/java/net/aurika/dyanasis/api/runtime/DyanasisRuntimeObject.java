package net.aurika.dyanasis.api.runtime;

import org.jetbrains.annotations.NotNull;

/**
 * An object that created in a dyanasis runtime, it must be known the dyanasis runtime that belongs.
 */
public interface DyanasisRuntimeObject extends DyanasisRuntimeAware {
    /**
     * Gets the dyanasis runtime to which this object belongs.
     *
     * @return the runtime
     */
    @Override
    @NotNull DyanasisRuntime dyanasisRuntime();
}

package net.aurika.dyanasis.api.runtime;

import org.jetbrains.annotations.NotNull;

public interface DyanasisRuntimeAware {
    @NotNull DyanasisRuntime dyanasisRuntime();
}

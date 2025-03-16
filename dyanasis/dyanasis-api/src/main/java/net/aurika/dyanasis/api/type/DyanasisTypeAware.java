package net.aurika.dyanasis.api.type;

import org.jetbrains.annotations.NotNull;

public interface DyanasisTypeAware {
    /**
     * Gets the dyanasis type.
     *
     * @return the dyanasis type
     */
    @NotNull DyanasisType<?> dyanasisType();
}

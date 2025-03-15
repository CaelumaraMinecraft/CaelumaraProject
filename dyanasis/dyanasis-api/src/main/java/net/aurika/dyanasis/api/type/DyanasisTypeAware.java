package net.aurika.dyanasis.api.type;

import org.jetbrains.annotations.NotNull;

public interface DyanasisTypeAware {
    @NotNull DyanasisType<?> dyanasisType();
}

package net.aurika.dyanasis.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface Named {
    @NotNull String name();
}

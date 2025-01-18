package top.auspice.services.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Service {
    default @Nullable Throwable checkAvailability() {
        return null;
    }

    default @NotNull String getServiceName() {
        return this.getClass().getSimpleName();
    }

    default void enable() {}

    default void disable() {}
}

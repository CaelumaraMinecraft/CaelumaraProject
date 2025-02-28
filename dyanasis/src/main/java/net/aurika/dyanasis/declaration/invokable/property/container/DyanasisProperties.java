package net.aurika.dyanasis.declaration.invokable.property.container;

import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface DyanasisProperties {
    /**
     * Gets the {@linkplain DyanasisProperties} that has none property.
     *
     * @return the empty properties
     */
    static @NotNull EmptyDyanasisProperties empty() {
        return EmptyDyanasisProperties.INSTANCE;
    }

    @Nullable DyanasisProperty getProperty(@NotNull String name);

    @NotNull Map<String, DyanasisProperty> allProperties();
}

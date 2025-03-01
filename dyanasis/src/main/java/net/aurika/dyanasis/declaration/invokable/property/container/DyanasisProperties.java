package net.aurika.dyanasis.declaration.invokable.property.container;

import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

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

    /**
     * Returns {@code true} if it has the property that equals the {@code name}.
     */
    boolean hasProperty(@NotNull String name);

    /**
     * Gets a property by the {@code name}.
     */
    @Nullable DyanasisProperty getProperty(@NotNull String name);

    /**
     * Gets all available properties.
     *
     * @return the properties
     */
    @Unmodifiable
    @NotNull Map<String, DyanasisProperty> allProperties();
}

package net.aurika.dyanasis.declaration.invokable.property.container;

import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;

public final class EmptyDyanasisProperties implements DyanasisProperties {
    public static final EmptyDyanasisProperties INSTANCE = new EmptyDyanasisProperties();

    private EmptyDyanasisProperties() {
    }

    @Override
    public boolean hasProperty(@NotNull String name) {
        return false;
    }

    @Override
    public @Nullable DyanasisProperty getProperty(@NotNull String name) {
        return null;
    }

    @Override
    public @Unmodifiable @NotNull Map<String, DyanasisProperty> allProperties() {
        return Collections.emptyMap();
    }
}

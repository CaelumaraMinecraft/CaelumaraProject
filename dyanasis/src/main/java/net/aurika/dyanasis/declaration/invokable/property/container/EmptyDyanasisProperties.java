package net.aurika.dyanasis.declaration.invokable.property.container;

import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EmptyDyanasisProperties implements DyanasisProperties {
    public static final EmptyDyanasisProperties INSTANCE = new EmptyDyanasisProperties();

    private EmptyDyanasisProperties() {
    }

    @Override
    public @Nullable DyanasisProperty getDyanasisProperty(@NotNull String name) {
        return null;
    }
}

package net.aurika.dyanasis.declaration.invokable.property.container;

import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisProperties {
    @Nullable DyanasisProperty getDyanasisProperty(@NotNull String name);
}

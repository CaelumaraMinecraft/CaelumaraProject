package net.aurika.dyanasis.declaration.invokable.property.container;

import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AbstractDyanasisProperties implements DyanasisProperties {
    protected final @NotNull Map<String, DyanasisProperty> properties;

    protected AbstractDyanasisProperties() {
        this(new HashMap<>());
    }

    protected AbstractDyanasisProperties(@NotNull Map<String, DyanasisProperty> properties) {
        Validate.Arg.notNull(properties, "properties");
        this.properties = properties;
    }

    @Override
    public @Nullable DyanasisProperty getProperty(@NotNull String name) {
        return properties.get(name);
    }

    @Override
    @Unmodifiable
    public @NotNull Map<String, DyanasisProperty> allProperties() {
        return Collections.unmodifiableMap(properties);
    }
}

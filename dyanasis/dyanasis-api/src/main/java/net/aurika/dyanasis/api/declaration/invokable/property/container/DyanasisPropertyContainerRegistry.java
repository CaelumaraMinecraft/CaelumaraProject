package net.aurika.dyanasis.api.declaration.invokable.property.container;

import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class DyanasisPropertyContainerRegistry extends AbstractDyanasisPropertyContainer {
    public DyanasisPropertyContainerRegistry() {
    }

    public DyanasisPropertyContainerRegistry(@NotNull Map<String, DyanasisProperty> properties) {
        super(properties);
    }

    /**
     * Adds a dyanasis property to this {@linkplain DyanasisPropertyContainerRegistry} and
     * returns the old property that contains the same name to {@code property}.
     *
     * @param property the property to add
     * @return the old property
     */
    public @Nullable DyanasisProperty addProperty(@NotNull DyanasisProperty property) {
        Validate.Arg.notNull(property, "property");
        String name = property.name();
        Objects.requireNonNull(name, "property.name()");
        return properties.put(name, property);
    }
}

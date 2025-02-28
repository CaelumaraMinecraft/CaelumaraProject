package net.aurika.dyanasis.declaration.invokable.property;

import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.util.DyanasisUtil;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * Provider of {@linkplain DyanasisProperties}.
 */
public interface DyanasisPropertiesProvider {
    /**
     * Gets the available dyanasis properties.
     *
     * @return the properties
     */
    @NotNull DyanasisProperties dyanasisProperties();

    static <M extends Map<String, DyanasisGetableProperty>> @NotNull M filterGetableProperties(@NotNull DyanasisPropertiesProvider dyPropertyContainer, @NotNull M container) {
        Validate.Arg.notNull(dyPropertyContainer, "dyPropertyContainer");
        Validate.Arg.notNull(container, "container");
        @Nullable Map<String, ? extends DyanasisProperty> dyProperties = dyPropertyContainer.dyanasisProperties();
        Objects.requireNonNull(dyProperties, "dyProperties");
        return DyanasisUtil.pickValueType(dyProperties, container, DyanasisGetableProperty.class);
    }

    static <M extends Map<String, DyanasisSetableProperty>> @NotNull M filterSetableProperties(@NotNull DyanasisPropertiesProvider dyPropertyContainer, @NotNull M container) {
        Validate.Arg.notNull(dyPropertyContainer, "dyPropertyContainer");
        Validate.Arg.notNull(container, "container");
        @Nullable Map<String, ? extends DyanasisProperty> dyProperties = dyPropertyContainer.dyanasisProperties();
        Objects.requireNonNull(dyProperties, "dyProperties");
        return DyanasisUtil.pickValueType(dyProperties, container, DyanasisSetableProperty.class);
    }
}

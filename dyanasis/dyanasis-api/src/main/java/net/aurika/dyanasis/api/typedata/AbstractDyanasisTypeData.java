package net.aurika.dyanasis.api.typedata;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaced;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class AbstractDyanasisTypeData implements DyanasisTypeData {

    private final @NotNull DyanasisNamespace namespace;
    private final @NotNull String name;

    public AbstractDyanasisTypeData(@NotNull DyanasisNamespace namespace, @NotNull String name) {
        Validate.Arg.notNull(namespace, "namespace");
        Validate.Arg.notNull(name, "name");
        this.namespace = namespace;
        this.name = name;
    }

    /**
     * Gets the full name of the type. It looks like {@code std.String}, {@code net.aurika.auspice.dyanasis.Namespace}
     *
     * @return the full name
     */
    @Override
    public @NotNull String fullName() {
        return fullName(".");
    }

    @Override
    public @NotNull String fullName(@NotNull String delimiter) {
        Validate.Arg.notNull(delimiter, "delimiter");
        return String.join(delimiter, namespace.path()) + delimiter + name;
    }

    @Override
    public @NotNull DyanasisNamespace dyanasisNamespace() {
        return namespace;
    }

    @Override
    public @NotNull String name() {
        return name;
    }
}

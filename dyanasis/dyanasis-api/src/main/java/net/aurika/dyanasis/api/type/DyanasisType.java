package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaced;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisType<O extends DyanasisObject> extends DyanasisTypeAware, DyanasisNamespaced, DyanasisRuntimeObject {
    boolean isInstance(DyanasisObject dyanasisObject);

    @NotNull String fullName();

    @NotNull String fullName(@NotNull String delimiter);

    @Override
    @NotNull DyanasisNamespace dyanasisNamespace();

    @NotNull String name();

    @NotNull Class<? extends DyanasisObject> clazz();

    @NotNull InstancePropertyHandler<O> instancePropertyHandler();

    @NotNull InstanceFunctionHandler<O> instanceFunctionHandler();

    @Override
    @NotNull DyanasisRuntime dyanasisRuntime();

    @Override
    default @NotNull DyanasisType<? extends O> dyanasisType() {
        return this;
    }
}

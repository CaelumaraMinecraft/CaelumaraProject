package net.aurika.dyanasis.api.typedata;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaced;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisTypeData<O extends DyanasisObject> extends DyanasisTypeDataAware, DyanasisNamespaced, DyanasisRuntimeObject {
    @NotNull String fullName();

    @NotNull String fullName(@NotNull String delimiter);

    @Override
    @NotNull DyanasisNamespace dyanasisNamespace();

    @NotNull String name();

    @NotNull InstancePropertyHandler<O> instancePropertyHandler();

    @NotNull InstanceFunctionHandler<O> instanceFunctionHandler();

    @Override
    @NotNull DyanasisRuntime dyanasisRuntime();

    @Override
    default @NotNull DyanasisTypeData<?> dyanasisTypeData() {
        return this;
    }
}

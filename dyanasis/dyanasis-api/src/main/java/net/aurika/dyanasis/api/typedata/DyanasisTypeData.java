package net.aurika.dyanasis.api.typedata;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaced;
import org.jetbrains.annotations.NotNull;

public interface DyanasisTypeData extends DyanasisTypeDataAware, DyanasisNamespaced {
    @NotNull String fullName();

    @NotNull String fullName(@NotNull String delimiter);

    @Override
    @NotNull DyanasisNamespace dyanasisNamespace();

    @NotNull String name();

    @Override
    @NotNull
    default DyanasisTypeData dyanasisTypeData() {
        return this;
    }
}

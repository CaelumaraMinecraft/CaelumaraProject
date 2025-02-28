package net.aurika.dyanasis.declaration.namespace;

import net.aurika.dyanasis.NamingContract;
import net.aurika.dyanasis.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.declaration.doc.DyanasisDocOwner;
import net.aurika.dyanasis.declaration.doc.DyanasisDocProvider;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionOwner;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionsProvider;
import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisPropertiesProvider;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisPropertyOwner;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface DyanasisNamespace extends DyanasisDeclaration,
        DyanasisPropertiesProvider, DyanasisPropertyOwner,
        DyanasisFunctionsProvider, DyanasisFunctionOwner,
        DyanasisDocProvider, DyanasisDocOwner
{
    /**
     * Gets the dyanasis namespace name.
     *
     * @return the dyanasis namespace name
     */
    @NamingContract.Namespace
    @NotNull String name();

    /**
     * Gets the path for the {@linkplain DyanasisNamespace}.
     *
     * @return the namespace path
     */
    @NotNull String @NotNull [] path();

    /**
     * Gets the parent namespace of this namespace
     *
     * @return the parent namespace
     */
    @Nullable DyanasisNamespace parent();

    /**
     * Gets a child dyanasis namespace from the child name.
     *
     * @param name the child namespace name
     * @return the get child namespace
     */
    default @Nullable DyanasisNamespace child(@NotNull String name) {
        return children().get(name);
    }

    /**
     * Gets children for this namespace.
     *
     * @return the subordinates
     */
    @NotNull Map<String, ? extends DyanasisNamespace> children();

    @Override
    @NotNull DyanasisProperties dyanasisProperties();

    @Override
    @NotNull DyanasisFunctions dyanasisFunctions();
}

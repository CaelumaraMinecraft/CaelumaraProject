package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAware;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionAnchor;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionsAware;
import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisPropertiesAware;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisPropertyAnchor;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface DyanasisNamespace extends DyanasisDeclaration,
        DyanasisPropertiesAware, DyanasisPropertyAnchor,
        DyanasisFunctionsAware, DyanasisFunctionAnchor,
        DyanasisDocAware, DyanasisDocAnchor {
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
     * Gets the parent namespace of this namespace. Returns {@code null} if this namespace is rooted.
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
    @Nullable DyanasisNamespace getChild(@NotNull String name);

    /**
     * Gets children for this namespace.
     *
     * @return the subordinates
     */
    @NotNull Map<String, ? extends DyanasisNamespace> children();

    @Override
    @NotNull DyanasisPropertyContainer<? extends DyanasisProperty> dyanasisProperties();

    @Override
    @NotNull DyanasisFunctionContainer<? extends DyanasisFunction> dyanasisFunctions();

    @Override
    @Nullable NamespaceDoc dyanasisDoc();

    interface NamespaceProperty extends DyanasisProperty {
        @Override
        @NotNull DyanasisNamespace owner();
    }

    interface NamespaceFunction extends DyanasisFunction {
        @Override
        @NotNull DyanasisNamespace owner();
    }

    interface NamespaceDoc extends DyanasisDoc {
        @Override
        @NotNull DyanasisNamespace owner();
    }
}

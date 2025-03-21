package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAware;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocEditable;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionAnchor;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionsAware;
import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisPropertiesAware;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisPropertyAnchor;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface DyanasisNamespace extends DyanasisDeclaration,
        DyanasisPropertiesAware, DyanasisPropertyAnchor,
        DyanasisFunctionsAware, DyanasisFunctionAnchor,
        DyanasisDocAware, DyanasisDocAnchor, DyanasisDocEditable<DyanasisNamespace.NamespaceDoc>,
        DyanasisRuntimeObject {

    @Deprecated
    static @NotNull StandardDyanasisNamespaceTree.StandardDyanasisNamespace createIfAbsent(@NotNull StandardDyanasisNamespaceTree tree, @NotNull DyanasisNamespacePath path) {
        Validate.Arg.notNull(tree, "tree");
        return tree.foundOrCreate(path);
    }

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
    @NotNull DyanasisNamespacePath path();

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
    @NotNull NamespacePropertyContainer<? extends NamespaceProperty> dyanasisProperties();

    @Override
    @NotNull NamespaceFunctionContainer<? extends NamespaceFunction> dyanasisFunctions();

    @Override
    @Nullable NamespaceDoc dyanasisDoc();

    @Override
    void dyanasisDoc(@Nullable NamespaceDoc doc);

    /**
     * Gets a {@linkplain DyanasisType} in the namespace by the type name.
     *
     * @param typename the type name
     * @return the type, can be null
     */
    @Nullable DyanasisType<?> getDyanasisType(@NotNull String typename);

    boolean hasDyanasisType(@NotNull String typename);

    /**
     * Gets all dyanasis types in this namespace.
     *
     * @return the all types.
     */
    @NotNull Map<String, ? extends DyanasisType<?>> dyanasisTypes();

    /**
     * Adds a {@linkplain DyanasisType} to this namespace.
     *
     * @param type the dyanasis type
     * @return the old type that has the same name
     * @throws IllegalArgumentException when the namespace of {@code type} doesn't equals this namespace
     */
    @Nullable DyanasisType<?> addDyanasisType(@NotNull DyanasisType<?> type);

    @Override
    @NotNull DyanasisRuntime dyanasisRuntime();

    @ApiStatus.Experimental
    interface NamespacePropertyContainer<P extends NamespaceProperty> extends DyanasisPropertyContainer<P>, NeedOwner {
        @Override
        @NotNull DyanasisNamespace owner();
    }

    @ApiStatus.Experimental
    interface NamespaceFunctionContainer<F extends NamespaceFunction> extends DyanasisFunctionContainer<F>, NeedOwner {
        @Override
        @NotNull DyanasisNamespace owner();
    }

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

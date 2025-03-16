package net.aurika.dyanasis.api.declaration.file;

import net.aurika.dyanasis.api.declaration.doc.DyanasisDocEditable;
import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractDyanasisFile<
        Properties extends DyanasisPropertyContainer<? extends DyanasisFile.FileProperty>,
        Functions extends DyanasisFunctionContainer<? extends DyanasisFile.FileFunction>,
        Doc extends DyanasisFile.FileDoc>
        implements DyanasisFile, DyanasisRuntimeObject, DyanasisDocEditable<Doc> {

    private final @NotNull DyanasisNamespace namespace;
    private @Nullable DyanasisNamespace usingNamespace;
    protected @NotNull Properties properties;
    protected @NotNull Functions functions;
    private @Nullable Doc doc;

    public AbstractDyanasisFile(@NotNull DyanasisNamespace namespace, @NotNull Properties properties, @NotNull Functions functions) {
        this(namespace, null, properties, functions, null);
    }

    public AbstractDyanasisFile(@NotNull DyanasisNamespace namespace,
                                @Nullable DyanasisNamespace usingNamespace,
                                @NotNull Properties properties,
                                @NotNull Functions functions,
                                @Nullable Doc doc
    ) {
        Validate.Arg.notNull(namespace, "namespace");
        Validate.Arg.notNull(properties, "properties");
        Validate.Arg.notNull(functions, "functions");
        this.namespace = namespace;
        this.usingNamespace = usingNamespace;
        this.properties = properties;
        this.functions = functions;
        this.doc = doc;
    }

    @Override
    public @NotNull DyanasisNamespace dyanasisNamespace() {
        return namespace;
    }

    @Override
    public @Nullable DyanasisNamespace usingNamespace() {
        return this.usingNamespace;
    }

    @Override
    public void usingNamespace(@Nullable DyanasisNamespace namespace) {
        usingNamespace = namespace;
    }

    @Override
    public @NotNull Properties dyanasisProperties() {
        return properties;
    }

    @Override
    public @NotNull Functions dyanasisFunctions() {
        return functions;
    }

    @Override
    public @Nullable Doc dyanasisDoc() {
        return doc;
    }

    @Override
    public void dyanasisDoc(@Nullable Doc doc) {
        this.doc = doc;
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
        return namespace.dyanasisRuntime();
    }
}

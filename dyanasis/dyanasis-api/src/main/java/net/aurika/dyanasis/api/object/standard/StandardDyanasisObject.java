package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespacePath;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DefaultDyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class StandardDyanasisObject<T, Lexer extends DyanasisLexer> extends DefaultDyanasisObject<T, Lexer> {

    public static final DyanasisNamespacePath STD_NS_PATH = DyanasisNamespacePath.path("std");

    protected static @NotNull DyanasisNamespace standardNS(@NotNull DyanasisRuntime runtime) {
        Validate.Arg.notNull(runtime, "runtime");
        return runtime.environment().namespaces().foundOrCreate(STD_NS_PATH);
    }

    @SuppressWarnings("rawtypes")
    protected static @NotNull DyanasisType standardType(@NotNull DyanasisRuntime runtime, @NotNull String typename, Supplier<DyanasisType> whenCreate) {
        return type(runtime, STD_NS_PATH, typename, whenCreate);
    }

    protected StandardDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @NotNull DyanasisType<? extends DefaultDyanasisObject<T, Lexer>> typeData) {
        super(runtime, value, lexer, typeData);
    }

    protected StandardDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @Nullable DefaultDyanasisObject.DefaultObjectDoc doc, @NotNull DyanasisType<? extends DefaultDyanasisObject<T, Lexer>> typeData) {
        super(runtime, value, lexer, doc, typeData);
    }
}

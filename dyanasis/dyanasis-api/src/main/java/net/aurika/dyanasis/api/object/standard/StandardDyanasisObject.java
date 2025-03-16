package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespacePath;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DefaultDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
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


    protected static @NotNull <C extends DyanasisObject, T extends DyanasisType<C>> T standardType(@NotNull DyanasisRuntime runtime, @NotNull String typename, Class<? extends C> clazz, Supplier<? extends T> whenCreate) {
        return type(runtime, STD_NS_PATH, typename, clazz, whenCreate);
    }

    protected StandardDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer) {
        super(runtime, value, lexer);
    }

    protected StandardDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @Nullable DefaultDyanasisObject.DefaultObjectDoc doc) {
        super(runtime, value, lexer, doc);
    }

    @Override
    public abstract @NotNull DyanasisType<? extends StandardDyanasisObject<T, Lexer>> dyanasisType();
}

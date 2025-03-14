package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.AbstractDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.typedata.AbstractDyanasisTypeData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public abstract class StandardDyanasisObject<T, Lexer extends DyanasisLexer> extends AbstractDyanasisObject<T, Lexer, DyanasisObject.ObjectDoc> {

    protected StandardDyanasisObject(T value, @NotNull Lexer lexer, @Nullable StandardObjectDoc doc, @NotNull AbstractDyanasisTypeData typeData) {
        super(value, lexer, doc, typeData);
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return false;
    }

    public static abstract class StandardObjectPropertyContainer extends AbstractObjectPropertyContainer<StandardObjectProperty> {

        public StandardObjectPropertyContainer() {
            super();
        }

        @Override
        public boolean hasProperty(@NotNull String name) {
            // TODO
        }

        @Override
        public @Nullable StandardObjectProperty getProperty(@NotNull String name) {
            // TODO
        }

        @Override
        public @Unmodifiable @NotNull Map<String, ? extends StandardObjectProperty> allProperties() {
            // TODO
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public static abstract class StandardObjectFunctionContainer extends AbstractObjectFunctionContainer<StandardObjectFunction> {

        protected  Map<DyanasisFunctionKey, ObjectFunction> protectedFunctions;

        protected StandardObjectFunctionContainer() {
            super();
        }

        @Override
        public boolean hasFunction(@NotNull DyanasisFunctionKey key) {
            // TODO
        }

        @Override
        public @Nullable StandardObjectFunction getFunction(@NotNull DyanasisFunctionKey key) {
            // TODO
        }

        @Override
        public @Unmodifiable @NotNull Map<DyanasisFunctionKey, ? extends StandardObjectFunction> allFunctions() {
            // TODO
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class StandardObjectProperty extends AbstractObjectProperty {
        public StandardObjectProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public abstract @NotNull DyanasisObject value();

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class StandardObjectFunction extends AbstractObjectFunction {

        public StandardObjectFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class StandardObjectDoc extends AbstractObjectDoc {
        public StandardObjectDoc(@NotNull String value) {
            super(value);
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }
}

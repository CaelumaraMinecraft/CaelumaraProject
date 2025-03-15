package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocEditable;
import net.aurika.dyanasis.api.declaration.invokable.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.typedata.DyanasisTypeData;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDyanasisObject<T, Lexer extends DyanasisLexer, Doc extends DyanasisObject.ObjectDoc> implements DyanasisObject, DyanasisDocEditable<Doc> {

    protected final @NotNull DyanasisRuntime runtime;
    protected T value;
    protected final @NotNull Lexer lexer;
    protected @Nullable Doc doc;
    protected final @NotNull DyanasisTypeData<? extends DyanasisObject> typeData;

    protected AbstractDyanasisObject(@NotNull DyanasisRuntime runtime,
                                     T value,
                                     @NotNull Lexer lexer,
                                     @Nullable Doc doc,
                                     @NotNull DyanasisTypeData<? extends DyanasisObject> typeData
    ) {
        Validate.Arg.notNull(runtime, "runtime");
        Validate.Arg.notNull(lexer, "lexer");
        Validate.Arg.notNull(typeData, "typeData");
        this.runtime = runtime;
        this.value = value;
        this.lexer = lexer;
        this.doc = doc;
        this.typeData = typeData;
    }

    /**
     * Gets the lexer of this dyanasis object.
     *
     * @return the lexer
     */
    public @NotNull Lexer lexer() {
        return lexer;
    }

    @Override
    public abstract @NotNull ObjectPropertyContainer<? extends DyanasisObject.ObjectProperty> dyanasisProperties();

    @Override
    public abstract @NotNull DyanasisObject.ObjectFunctionContainer<? extends DyanasisObject.ObjectFunction> dyanasisFunctions();

    @Override
    public @Nullable Doc dyanasisDoc() {
        return doc;
    }

    @Override
    public void dyanasisDoc(@Nullable Doc doc) {
        this.doc = doc;
    }

    @Override
    public @NotNull DyanasisTypeData<? extends DyanasisObject> dyanasisTypeData() {
        return typeData;
    }

    @Override
    public @NotNull T valueAsJava() {
        return value;
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
        return runtime;
    }

    public interface AbstractObjectPropertyContainer<Property extends ObjectProperty> extends ObjectPropertyContainer<Property> {
        @Override
        @NotNull DyanasisObject owner();
    }

    public interface AbstractObjectFunctionContainer<Function extends ObjectFunction> extends ObjectFunctionContainer<Function> {
        @Override
        @NotNull DyanasisObject owner();
    }

    public abstract static class AbstractObjectProperty extends AbstractDyanasisProperty implements ObjectProperty {
        public AbstractObjectProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public abstract @NotNull DyanasisObject value();

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class AbstractObjectFunction extends AbstractDyanasisFunction implements ObjectFunction {
        public AbstractObjectFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class AbstractObjectDoc implements ObjectDoc {
        protected @NotNull String value;

        public AbstractObjectDoc(@NotNull String value) {
            this.value = value;
        }

        @Override
        public @NotNull String value() {
            return value;
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }
}

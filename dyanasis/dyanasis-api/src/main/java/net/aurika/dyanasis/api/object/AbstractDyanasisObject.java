package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocEditable;
import net.aurika.dyanasis.api.declaration.invokable.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.typedata.AbstractDyanasisTypeData;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDyanasisObject<T, Lexer extends DyanasisLexer, Doc extends DyanasisObject.ObjectDoc> implements DyanasisObject, DyanasisDocEditable<Doc> {
    protected T value;
    protected final @NotNull Lexer lexer;
    protected @Nullable Doc doc;
    protected final @NotNull AbstractDyanasisTypeData typeData;

    protected AbstractDyanasisObject(T value,
                                     @NotNull Lexer lexer,
                                     @Nullable Doc doc,
                                     @NotNull AbstractDyanasisTypeData typeData
    ) {
        Validate.Arg.notNull(lexer, "lexer");
        Validate.Arg.notNull(typeData, "typeData");
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
    public @NotNull AbstractDyanasisTypeData dyanasisTypeData() {
        return typeData;
    }

    @Override
    public @NotNull T valueAsJava() {
        return value;
    }

    public abstract static class AbstractObjectPropertyContainer<Property extends ObjectProperty> implements ObjectPropertyContainer<Property> {

        @Override
        public @NotNull AbstractDyanasisTypeData objectTypeData() {
            return owner().dyanasisTypeData();
        }
    }

    public abstract static class AbstractObjectFunctionContainer<Function extends ObjectFunction> implements ObjectFunctionContainer<Function> {

        @Override
        public @NotNull AbstractDyanasisTypeData objectTypeData() {
            return owner().dyanasisTypeData();
        }
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

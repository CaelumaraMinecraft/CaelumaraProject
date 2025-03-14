package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocEditable;
import net.aurika.dyanasis.api.declaration.invokable.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.typedata.DyanasisTypeData;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDyanasisObject<
        T,
        Lexer extends DyanasisLexer,
        Properties extends DyanasisObject.ObjectPropertyContainer<? extends DyanasisObject.ObjectProperty>,
        Functions extends DyanasisObject.ObjectFunctionContainer<? extends DyanasisObject.ObjectFunction>,
        Doc extends DyanasisObject.ObjectDoc
        > implements DyanasisObject,
        DyanasisDocEditable<Doc> {
    protected T value;
    protected final @NotNull Lexer lexer;
    protected @NotNull Properties properties;
    protected @NotNull Functions functions;
    protected @Nullable Doc doc;
    protected final @NotNull DyanasisTypeData typeData;

    protected AbstractDyanasisObject(T value, @NotNull Lexer lexer, @NotNull DyanasisTypeData typeData) {

    }

    protected AbstractDyanasisObject(T value,
                                     @NotNull Lexer lexer,
                                     @NotNull Properties properties,
                                     @NotNull Functions functions,
                                     @Nullable Doc doc,
                                     @NotNull DyanasisTypeData typeData
    ) {
        Validate.Arg.notNull(lexer, "lexer");
        Validate.Arg.notNull(properties, "properties");
        Validate.Arg.notNull(functions, "functions");
        Validate.Arg.notNull(typeData, "typeData");
        this.value = value;
        this.lexer = lexer;
        this.properties = properties;
        this.functions = functions;
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
    public @NotNull DyanasisTypeData dyanasisTypeData() {
        return typeData;
    }

    @Override
    public @NotNull T valueAsJava() {
        return value;
    }

    public abstract class AbstractObjectPropertyContainer<Property extends ObjectProperty> implements ObjectPropertyContainer<Property> {
        @Override
        public @NotNull DyanasisTypeData objectTypeData() {
            return AbstractDyanasisObject.this.typeData;
        }
    }

    public abstract class AbstractObjectFunctionContainer<Function extends ObjectFunction> implements ObjectFunctionContainer<Function> {
        @Override
        public @NotNull DyanasisTypeData objectTypeData() {
            return AbstractDyanasisObject.this.typeData;
        }
    }

    public abstract static class AbstractProperty<Anchored extends DyanasisObject> extends AbstractDyanasisProperty<Anchored> {
        public AbstractProperty(@NamingContract.Invokable final @NotNull String name, @NotNull Anchored anchored) {
            super(name, anchored);
        }

        @Override
        public abstract @NotNull DyanasisObject value();
    }

    public abstract static class AbstractFunction<Anchored extends DyanasisObject> extends AbstractDyanasisFunction<Anchored> {
        public AbstractFunction(@NotNull DyanasisFunctionKey key, @NotNull Anchored anchored) {
            super(key, anchored);
        }
    }
}

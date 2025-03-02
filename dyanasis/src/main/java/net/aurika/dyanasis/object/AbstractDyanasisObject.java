package net.aurika.dyanasis.object;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.lexer.DyanasisLexerSettings;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisObject<T> implements DyanasisObject {
    protected T value;
    protected final @NotNull DyanasisLexerSettings settings;

    protected AbstractDyanasisObject(T value, @NotNull DyanasisLexerSettings settings) {
        Validate.Arg.notNull(settings, "settings");
        this.value = value;
        this.settings = settings;
    }

    /**
     * Gets the lexer settings of this dyanasis object.
     *
     * @return the settings
     */
    public @NotNull DyanasisLexerSettings settings() {
        return settings;
    }

    @Override
    public abstract @NotNull DyanasisProperties dyanasisProperties();

    @Override
    public abstract @NotNull DyanasisFunctions dyanasisFunctions();

    @Override
    public @NotNull T valueAsJava() {
        return value;
    }

    public abstract class AbstractProperty implements DyanasisProperty {

        @Override
        public @NotNull AbstractDyanasisObject<T> owner() {
            return AbstractDyanasisObject.this;
        }
    }

    public abstract class AbstractFunction implements DyanasisFunction {
        protected final @NotNull DyanasisFunctionKey key;

        public AbstractFunction(@NotNull DyanasisFunctionKey key) {
            Validate.Arg.notNull(key, "key");
            this.key = key;
        }

        @Override
        public @NotNull DyanasisFunctionKey key() {
            return key;
        }

        @Override
        public @NotNull AbstractDyanasisObject<T> owner() {
            return AbstractDyanasisObject.this;
        }
    }
}

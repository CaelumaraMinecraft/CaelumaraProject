package net.aurika.dyanasis.object;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionOwner;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbstractDyanasisObject implements DyanasisObject {

    @Override
    public abstract @NotNull Map<String, ? extends DyanasisProperty> dyanasisProperties();

    @Override
    public abstract @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions();

    public abstract class Property implements DyanasisProperty {

        @Override
        public @NotNull AbstractDyanasisObject owner() {
            return AbstractDyanasisObject.this;
        }
    }

    public abstract class Function implements DyanasisFunction {
        protected final @NotNull DyanasisFunctionKey key;

        public Function(@NotNull DyanasisFunctionKey key) {
            Validate.Arg.notNull(key, "key");
            this.key = key;
        }

        @Override
        public @NotNull DyanasisFunctionKey key() {
            return key;
        }

        @Override
        public @NotNull DyanasisFunctionOwner owner() {
            return AbstractDyanasisObject.this;
        }
    }
}

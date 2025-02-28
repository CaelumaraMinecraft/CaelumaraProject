package net.aurika.dyanasis.object;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionOwner;
import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisObject implements DyanasisObject {

    @Override
    public abstract @NotNull DyanasisProperties dyanasisProperties();

    @Override
    public abstract @NotNull DyanasisFunctions dyanasisFunctions();

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

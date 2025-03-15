package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObjectNumber;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectNumber<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Number, Lexer> implements DyanasisObjectNumber {

    public static final String TYPE_NAME = "Number";

    protected @NotNull StandardObjectNumberPropertyContainer properties = new StandardObjectNumberPropertyContainer();
    protected @NotNull StandardObjectNumberFunctionContainer functions = new StandardObjectNumberFunctionContainer();

    public StandardDyanasisObjectNumber(@NotNull DyanasisRuntime runtime, Number value, @NotNull Lexer lexer) {
        this(runtime, value, lexer, null, standardType(runtime, TYPE_NAME, () -> new StandardNumberType(runtime, standardNS(runtime))));
    }

    protected StandardDyanasisObjectNumber(@NotNull DyanasisRuntime runtime, Number value, @NotNull Lexer lexer, @Nullable DefaultObjectDoc doc, @NotNull DyanasisType<?> type) {
        super(runtime, value, lexer, doc, type);
    }

    @Override
    public @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties() {
        return properties;
    }

    @Override
    public @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions() {
        return functions;
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return toString().equals(cfgStr);
        //TODO compile and equals
    }

    public class StandardObjectNumberPropertyContainer extends DefaultObjectPropertyContainer {
        @Override
        public @NotNull StandardDyanasisObjectNumber<Lexer> owner() {
            return StandardDyanasisObjectNumber.this;
        }
    }

    public class StandardObjectNumberFunctionContainer extends DefaultObjectFunctionContainer {
        @Override
        public @NotNull StandardDyanasisObjectNumber<Lexer> owner() {
            return StandardDyanasisObjectNumber.this;
        }
    }

    public static class StandardNumberType extends DefaultObjectType<StandardDyanasisObjectNumber<?>> {
        public StandardNumberType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
            super(runtime, namespace, StandardDyanasisObjectNumber.TYPE_NAME);
        }
    }
}

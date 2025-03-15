package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DefaultDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectMap;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisObjectMap<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Map<?, ?>, Lexer> implements DyanasisObjectMap {

    public static final String TYPE_NAME = "Map";

    public static final String PROPERTY_SIZE = "size";

    protected @NotNull StandardObjectMapPropertyContainer properties = new StandardObjectMapPropertyContainer();
    protected @NotNull StandardObjectMapFunctionContainer functions = new StandardObjectMapFunctionContainer();

    public StandardDyanasisObjectMap(@NotNull DyanasisRuntime runtime, Map<?, ?> value, @NotNull Lexer lexer) {
        this(runtime, value, lexer, null, standardType(runtime, TYPE_NAME, () -> new StandardMapType(runtime, standardNS(runtime))));
    }

    protected StandardDyanasisObjectMap(@NotNull DyanasisRuntime runtime, Map<?, ?> value, @NotNull Lexer lexer, DefaultObjectDoc doc, @NotNull DyanasisType<? extends DefaultDyanasisObject<Map<?, ?>, Lexer>> type) {
        super(runtime, value, lexer, doc, type);
    }

    @Override
    public int size() {
        return value.size();
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
        //TODO lex and equals
        return false;
    }

    public class StandardObjectMapPropertyContainer extends DefaultObjectPropertyContainer {
        @Override
        public @NotNull DyanasisObject owner() {
            return StandardDyanasisObjectMap.this;
        }
    }

    public class StandardObjectMapFunctionContainer extends DefaultObjectFunctionContainer {
        @Override
        public @NotNull DyanasisObject owner() {
            return StandardDyanasisObjectMap.this;
        }
    }

    public class Size extends AbstractMapProperty implements DyanasisGetableProperty {
        public Size() {
            super(PROPERTY_SIZE);
        }

        @Override
        public @NotNull StandardDyanasisObjectNumber<? extends Lexer> value() {
            return new StandardDyanasisObjectNumber<>(runtime, value.size(), lexer);
        }

        @Override
        public @NotNull StandardDyanasisObjectNumber<? extends Lexer> getPropertyValue() {
            return value();
        }
    }

    public abstract class AbstractMapProperty extends DefaultObjectProperty {
        public AbstractMapProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public @NotNull StandardDyanasisObjectMap<Lexer> owner() {
            return StandardDyanasisObjectMap.this;
        }

        @Override
        public @NotNull DyanasisRuntime dyanasisRuntime() {
            return StandardDyanasisObjectMap.this.dyanasisRuntime();
        }
    }

    public abstract class AbstractMapFunction extends DefaultObjectFunction {
        public AbstractMapFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

        @Override
        public @NotNull StandardDyanasisObjectMap<Lexer> owner() {
            return StandardDyanasisObjectMap.this;
        }

        @Override
        public @NotNull DyanasisRuntime dyanasisRuntime() {
            return StandardDyanasisObjectMap.this.dyanasisRuntime();
        }
    }

    public static class StandardMapType extends DefaultObjectType<StandardDyanasisObjectMap<?>> {

        public StandardMapType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
            super(runtime, namespace, StandardDyanasisObjectMap.TYPE_NAME);
        }

        protected void addProperties() {
            properties.put(PROPERTY_SIZE, (mapObject) -> mapObject.new Size());
        }
    }
}

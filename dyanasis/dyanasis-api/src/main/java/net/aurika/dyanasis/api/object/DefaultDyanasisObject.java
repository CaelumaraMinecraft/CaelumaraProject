package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.AbstractDyanasisType;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.InstanceFunctionHandler;
import net.aurika.dyanasis.api.type.InstancePropertyHandler;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * {@linkplain DyanasisObject} 的默认实现. 自定义的类可以继承此类.
 */
public abstract class DefaultDyanasisObject<T, Lexer extends DyanasisLexer> extends AbstractDyanasisObject<T, Lexer, DefaultDyanasisObject.DefaultObjectDoc> implements DyanasisObject {

    protected DefaultDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @NotNull DyanasisType<? extends DefaultDyanasisObject<T, Lexer>> typeData) {
        this(runtime, value, lexer, null, typeData);
    }

    protected DefaultDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @Nullable DefaultDyanasisObject.DefaultObjectDoc doc, @NotNull DyanasisType<? extends DefaultDyanasisObject<T, Lexer>> typeData) {
        super(runtime, value, lexer, doc, typeData);
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return false;
    }

    public static abstract class DefaultObjectPropertyContainer implements AbstractObjectPropertyContainer<DefaultObjectProperty> {

        protected final @NotNull Map<String, DefaultObjectProperty> registry;

        protected DefaultObjectPropertyContainer() {
            this(new HashMap<>());
        }

        protected DefaultObjectPropertyContainer(@NotNull Map<String, DefaultObjectProperty> registry) {
            super();
            this.registry = registry;
        }

        @Override
        public boolean hasProperty(@NotNull String key) {
            return protectedProperties().containsKey(key) || registry.containsKey(key);
        }

        @Override
        public @Nullable DefaultDyanasisObject.DefaultObjectProperty getProperty(@NotNull String key) {
            Map<String, DefaultObjectProperty> protectedProps = protectedProperties();
            return protectedProps.containsKey(key) ? protectedProps.get(key) : registry.get(key);
        }

        @Override
        public @Unmodifiable @NotNull Map<String, ? extends DefaultObjectProperty> allProperties() {
            Map<String, DefaultObjectProperty> props = new HashMap<>();
            props.putAll(registry);
            props.putAll(protectedProperties());
            return Collections.unmodifiableMap(props);
        }

        protected @NotNull Map<String, DefaultObjectProperty> protectedProperties() {
            return ownerType().instancePropertyHandler().handle(owner());
        }

        protected @NotNull DyanasisType ownerType() {
            return owner().dyanasisType();
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public static abstract class DefaultObjectFunctionContainer implements AbstractObjectFunctionContainer<DefaultObjectFunction> {

        protected final @NotNull Map<DyanasisFunctionKey, DefaultObjectFunction> registry;

        protected DefaultObjectFunctionContainer() {
            this(new HashMap<>());
        }

        protected DefaultObjectFunctionContainer(@NotNull Map<DyanasisFunctionKey, DefaultObjectFunction> registry) {
            super();
            Validate.Arg.notNull(registry, "registry");
            this.registry = registry;
        }

        @Override
        public boolean hasFunction(@NotNull DyanasisFunctionKey key) {
            return protectedFunctions().containsKey(key) || registry.containsKey(key);
        }

        @Override
        public @Nullable DefaultDyanasisObject.DefaultObjectFunction getFunction(@NotNull DyanasisFunctionKey key) {
            var protectedFns = protectedFunctions();
            return protectedFns.containsKey(key) ? protectedFns.get(key) : registry.get(key);
        }

        @Override
        public @Unmodifiable @NotNull Map<DyanasisFunctionKey, ? extends DefaultObjectFunction> allFunctions() {
            Map<DyanasisFunctionKey, DefaultObjectFunction> fns = new HashMap<>();
            fns.putAll(registry);
            fns.putAll(protectedFunctions());
            return fns;
        }

        protected @NotNull Map<DyanasisFunctionKey, DefaultObjectFunction> protectedFunctions() {
            return ownerType().instanceFunctionHandler().handle(owner());
        }

        protected @NotNull DyanasisType ownerType() {
            return owner().dyanasisType();
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class DefaultObjectProperty extends AbstractObjectProperty {
        public DefaultObjectProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public abstract @NotNull DyanasisObject value();

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class DefaultObjectFunction extends AbstractObjectFunction {

        public DefaultObjectFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public abstract static class DefaultObjectDoc extends AbstractObjectDoc {
        public DefaultObjectDoc(@NotNull String value) {
            super(value);
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public static class DefaultObjectType<O extends DyanasisObject> extends AbstractDyanasisType<O> {

        protected final Map<String, Function<O, DyanasisObject.ObjectProperty>> properties;
        protected final Map<DyanasisFunctionKey, Function<O, DyanasisObject.ObjectFunction>> functions;

        public DefaultObjectType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace, @NotNull String name) {
            this(runtime, namespace, name, new HashMap<>(), new HashMap<>());
        }

        public DefaultObjectType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace, @NotNull String name, Map<String, Function<O, ObjectProperty>> properties, Map<DyanasisFunctionKey, Function<O, ObjectFunction>> functions) {
            super(runtime, namespace, name);
            this.properties = properties;
            this.functions = functions;
        }

        @Override
        public @NotNull InstancePropertyHandler<O> instancePropertyHandler() {
            return (object) -> {
                Map<String, ObjectProperty> props = new HashMap<>();
                for (Map.Entry<String, Function<O, ObjectProperty>> entry : DefaultObjectType.this.properties.entrySet()) {
                    props.put(entry.getKey(), entry.getValue().apply(object));
                }
                return props;
            };
        }

        @Override
        public @NotNull InstanceFunctionHandler<O> instanceFunctionHandler() {
            return (object) -> {
                Map<DyanasisFunctionKey, ObjectFunction> fns = new HashMap<>();
                for (Map.Entry<DyanasisFunctionKey, Function<O, ObjectFunction>> entry : DefaultObjectType.this.functions.entrySet()) {
                    fns.put(entry.getKey(), entry.getValue().apply(object));
                }
                return fns;
            };
        }
    }
}

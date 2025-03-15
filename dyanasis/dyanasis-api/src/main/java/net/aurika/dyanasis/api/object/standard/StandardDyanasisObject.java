package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.AbstractDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.typedata.AbstractDyanasisTypeData;
import net.aurika.dyanasis.api.typedata.DyanasisTypeData;
import net.aurika.dyanasis.api.typedata.InstanceFunctionHandler;
import net.aurika.dyanasis.api.typedata.InstancePropertyHandler;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * {@linkplain DyanasisObject} 的标准实现.
 */
public abstract class StandardDyanasisObject<T, Lexer extends DyanasisLexer> extends AbstractDyanasisObject<T, Lexer, StandardDyanasisObject.StandardObjectDoc> implements DyanasisObject {

    protected StandardDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @NotNull AbstractDyanasisTypeData<? extends StandardDyanasisObject<T, Lexer>> typeData) {
        this(runtime, value, lexer, null, typeData);
    }

    protected StandardDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @Nullable StandardObjectDoc doc, @NotNull AbstractDyanasisTypeData<? extends StandardDyanasisObject<T, Lexer>> typeData) {
        super(runtime, value, lexer, doc, typeData);
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return false;
    }

    public static abstract class StandardObjectPropertyContainer implements AbstractObjectPropertyContainer<StandardObjectProperty> {

        protected final @NotNull Map<String, StandardObjectProperty> registry;

        protected StandardObjectPropertyContainer() {
            this(new HashMap<>());
        }

        protected StandardObjectPropertyContainer(@NotNull Map<String, StandardObjectProperty> registry) {
            super();
            this.registry = registry;
        }

        @Override
        public boolean hasProperty(@NotNull String key) {
            return protectedProperties().containsKey(key) || registry.containsKey(key);
        }

        @Override
        public @Nullable StandardObjectProperty getProperty(@NotNull String key) {
            Map<String, StandardObjectProperty> protectedProps = protectedProperties();
            return protectedProps.containsKey(key) ? protectedProps.get(key) : registry.get(key);
        }

        @Override
        public @Unmodifiable @NotNull Map<String, ? extends StandardObjectProperty> allProperties() {
            Map<String, StandardObjectProperty> props = new HashMap<>();
            props.putAll(registry);
            props.putAll(protectedProperties());
            return Collections.unmodifiableMap(props);
        }

        protected @NotNull Map<String, StandardObjectProperty> protectedProperties() {
            return ownerType().instancePropertyHandler().handle(owner());
        }

        protected @NotNull DyanasisTypeData ownerType() {
            return owner().dyanasisTypeData();
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public static abstract class StandardObjectFunctionContainer implements AbstractObjectFunctionContainer<StandardObjectFunction> {

        protected final @NotNull Map<DyanasisFunctionKey, StandardObjectFunction> registry;

        protected StandardObjectFunctionContainer() {
            this(new HashMap<>());
        }

        protected StandardObjectFunctionContainer(@NotNull Map<DyanasisFunctionKey, StandardObjectFunction> registry) {
            super();
            Validate.Arg.notNull(registry, "registry");
            this.registry = registry;
        }

        @Override
        public boolean hasFunction(@NotNull DyanasisFunctionKey key) {
            return protectedFunctions().containsKey(key) || registry.containsKey(key);
        }

        @Override
        public @Nullable StandardObjectFunction getFunction(@NotNull DyanasisFunctionKey key) {
            var protectedFns = protectedFunctions();
            return protectedFns.containsKey(key) ? protectedFns.get(key) : registry.get(key);
        }

        @Override
        public @Unmodifiable @NotNull Map<DyanasisFunctionKey, ? extends StandardObjectFunction> allFunctions() {
            Map<DyanasisFunctionKey, StandardObjectFunction> fns = new HashMap<>();
            fns.putAll(registry);
            fns.putAll(protectedFunctions());
            return fns;
        }

        protected @NotNull Map<DyanasisFunctionKey, StandardObjectFunction> protectedFunctions() {
            return ownerType().instanceFunctionHandler().handle(owner());
        }

        protected @NotNull DyanasisTypeData ownerType() {
            return owner().dyanasisTypeData();
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

    public static class StandardTypeData<O extends DyanasisObject> extends AbstractDyanasisTypeData<O> {

        protected final Map<String, Function<O, DyanasisObject.ObjectProperty>> properties;
        protected final Map<DyanasisFunctionKey, Function<O, DyanasisObject.ObjectFunction>> functions;

        public StandardTypeData(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace, @NotNull String name) {
            this(runtime, namespace, name, new HashMap<>(), new HashMap<>());
        }

        public StandardTypeData(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace, @NotNull String name, Map<String, Function<O, ObjectProperty>> properties, Map<DyanasisFunctionKey, Function<O, ObjectFunction>> functions) {
            super(runtime, namespace, name);
            this.properties = properties;
            this.functions = functions;
        }

        @Override
        public @NotNull InstancePropertyHandler<O> instancePropertyHandler() {
            return (object) -> {
                Map<String, ObjectProperty> props = new HashMap<>();
                for (Map.Entry<String, Function<O, ObjectProperty>> entry : StandardTypeData.this.properties.entrySet()) {
                    props.put(entry.getKey(), entry.getValue().apply(object));
                }
                return props;
            };
        }

        @Override
        public @NotNull InstanceFunctionHandler<O> instanceFunctionHandler() {
            return (object) -> {
                Map<DyanasisFunctionKey, ObjectFunction> fns = new HashMap<>();
                for (Map.Entry<DyanasisFunctionKey, Function<O, ObjectFunction>> entry : StandardTypeData.this.functions.entrySet()) {
                    fns.put(entry.getKey(), entry.getValue().apply(object));
                }
                return fns;
            };
        }
    }
}

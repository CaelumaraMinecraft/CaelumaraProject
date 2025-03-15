package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.DyanasisInvokable;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.AbstractDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.typedata.AbstractDyanasisTypeData;
import net.aurika.dyanasis.api.typedata.InstanceFunctionHandler;
import net.aurika.dyanasis.api.typedata.InstancePropertyHandler;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

        protected final @NotNull StandardObjectInvokableRegistry<String, StandardObjectProperty> registry;

        protected StandardObjectPropertyContainer() {
            this(new StandardObjectInvokableRegistry<>());
        }

        protected StandardObjectPropertyContainer(@NotNull StandardObjectInvokableRegistry<String, StandardObjectProperty> registry) {
            super();
            this.registry = registry;
        }

        @Override
        public boolean hasProperty(@NotNull String key) {
            return registry.containsKey(key);
        }

        @Override
        public @Nullable StandardObjectProperty getProperty(@NotNull String key) {
            return registry.getInvokable(key);
        }

        @Override
        public @Unmodifiable @NotNull Map<String, ? extends StandardObjectProperty> allProperties() {
            return Collections.unmodifiableMap(registry.putAllTo(new HashMap<>()));
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public static abstract class StandardObjectFunctionContainer implements AbstractObjectFunctionContainer<StandardObjectFunction> {

        protected final @NotNull StandardObjectInvokableRegistry<DyanasisFunctionKey, StandardObjectFunction> registry;

        protected StandardObjectFunctionContainer() {
            this(new StandardObjectInvokableRegistry<>());
        }

        protected StandardObjectFunctionContainer(@NotNull StandardObjectInvokableRegistry<DyanasisFunctionKey, StandardObjectFunction> registry) {
            super();
            this.registry = registry;
        }

        @Override
        public boolean hasFunction(@NotNull DyanasisFunctionKey key) {
            return registry.containsKey(key);
        }

        @Override
        public @Nullable StandardObjectFunction getFunction(@NotNull DyanasisFunctionKey key) {
            return registry.getInvokable(key);
        }

        @Override
        public @Unmodifiable @NotNull Map<DyanasisFunctionKey, ? extends StandardObjectFunction> allFunctions() {
            return Collections.unmodifiableMap(registry.putAllTo(new HashMap<>()));
        }

        @Override
        public abstract @NotNull DyanasisObject owner();
    }

    public static class StandardObjectInvokableRegistry<K, Invokable extends DyanasisInvokable> {

        protected final @NotNull Map<K, InvokableReserveEntry<Invokable>> invokableEntries;

        public StandardObjectInvokableRegistry() {
            this(new HashMap<>());
        }

        public StandardObjectInvokableRegistry(@NotNull Map<K, InvokableReserveEntry<Invokable>> invokableEntries) {
            Validate.Arg.notNull(invokableEntries, "invokableEntries");
            this.invokableEntries = invokableEntries;
        }

        protected @Nullable Invokable addInvokable(@NotNull K key, @NotNull InvokableReserve reserve, @NotNull Invokable invokable) {
            @Nullable InvokableReserveEntry<Invokable> present = invokableEntries.get(key);
            invokableEntries.put(key, new InvokableReserveEntry<>(reserve, invokable));
            return present != null ? present.invokable : null;
        }

        public @Nullable Invokable getInvokable(@NotNull K key) {
            @Nullable InvokableReserveEntry<Invokable> entry = invokableEntries.get(key);
            return entry != null ? entry.invokable : null;
        }

        public boolean containsKey(@NotNull K key) {
            return invokableEntries.containsKey(key);
        }

        @Contract("_ -> param1")
        public <M extends Map<K, Invokable>> @NotNull M putAllTo(@NotNull M map) {
            for (Map.Entry<K, InvokableReserveEntry<Invokable>> entry : invokableEntries.entrySet()) {
                map.put(entry.getKey(), entry.getValue().invokable());
            }
            return map;
        }

        public @Nullable Boolean isProtected(@NotNull K key) {
            return getReserve(key) == InvokableReserve.PROTECTED;
        }

        public @Nullable Boolean isDynamic(@NotNull K key) {
            return getReserve(key) == InvokableReserve.DYNAMIC;
        }

        public @Nullable InvokableReserve getReserve(@NotNull K key) {
            InvokableReserveEntry<Invokable> entry = invokableEntries.get(key);
            return entry != null ? entry.reserve : null;
        }
    }

    public static final class InvokableReserveEntry<Invokable extends DyanasisInvokable> {
        private final @NotNull InvokableReserve reserve;
        private final @NotNull Invokable invokable;

        public InvokableReserveEntry(@NotNull InvokableReserve reserve, @NotNull Invokable invokable) {
            Validate.Arg.notNull(reserve, "reserve");
            Validate.Arg.notNull(invokable, "invokable");
            this.reserve = reserve;
            this.invokable = invokable;
        }

        public @NotNull InvokableReserve reserve() {
            return reserve;
        }

        public @NotNull Invokable invokable() {
            return invokable;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof InvokableReserveEntry<?> that)) return false;
            return reserve == that.reserve && Objects.equals(invokable, that.invokable);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reserve, invokable);
        }

        @Override
        public String toString() {
            return "InvokableReserveEntry{reserve=" + reserve + ", invokable=" + invokable + "}";
        }
    }

    public enum InvokableReserve {
        /**
         * The {@linkplain DyanasisInvokable} is protected, it can't be added or removed externally.
         */
        PROTECTED,
        /**
         * The {@linkplain DyanasisInvokable} is dynamic, it can be registered or unregistered externally.
         */
        DYNAMIC,
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

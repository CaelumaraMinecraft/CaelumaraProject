package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.declaration.member.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
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
public abstract class DefaultDyanasisObject<T, Lexer extends DyanasisCompiler> extends AbstractDyanasisObject<T, Lexer, DefaultDyanasisObject.DefaultObjectDoc> {

  protected DefaultDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer) {
    this(runtime, value, lexer, null);
  }

  protected DefaultDyanasisObject(@NotNull DyanasisRuntime runtime, T value, @NotNull Lexer lexer, @Nullable DefaultDyanasisObject.DefaultObjectDoc doc) {
    super(runtime, value, lexer, doc);
  }

  @Override
  public abstract boolean equals(@NotNull String cfgStr);

  @Override
  public abstract @NotNull DyanasisType<? extends DefaultDyanasisObject<T, Lexer>> dyanasisType();

  public static abstract class DefaultObjectPropertyContainer<P extends ObjectProperty> implements AbstractObjectPropertyContainer<P> {

    protected final @NotNull Map<String, P> registry;

    protected DefaultObjectPropertyContainer() {
      this(new HashMap<>());
    }

    protected DefaultObjectPropertyContainer(@NotNull Map<String, P> registry) {
      super();
      this.registry = registry;
    }

    @Override
    public boolean hasProperty(@NotNull String key) {
      return protectedProperties().containsKey(key) || registry.containsKey(key);
    }

    @Override
    public @Nullable P getProperty(@NotNull String key) {
      Map<String, P> protectedProps = protectedProperties();
      return protectedProps.containsKey(key) ? protectedProps.get(key) : registry.get(key);
    }

    @Override
    public @Unmodifiable @NotNull Map<String, ? extends P> allProperties() {
      Map<String, P> props = new HashMap<>();
      props.putAll(registry);
      props.putAll(protectedProperties());
      return Collections.unmodifiableMap(props);
    }

    protected @NotNull Map<String, P> protectedProperties() {
      return ownerType().instancePropertyHandler().handle(owner());
    }

    protected @NotNull DyanasisType ownerType() {
      return owner().dyanasisType();
    }

    @Override
    public abstract @NotNull DyanasisObject owner();

  }

  public static abstract class DefaultObjectFunctionContainer<F extends ObjectFunction> implements AbstractObjectFunctionContainer<F> {

    protected final @NotNull Map<DyanasisFunctionSignature, F> registry;

    protected DefaultObjectFunctionContainer() {
      this(new HashMap<>());
    }

    protected DefaultObjectFunctionContainer(@NotNull Map<DyanasisFunctionSignature, F> registry) {
      super();
      Validate.Arg.notNull(registry, "registry");
      this.registry = registry;
    }

    @Override
    public boolean hasFunction(@NotNull DyanasisFunctionSignature key) {
      return protectedFunctions().containsKey(key) || registry.containsKey(key);
    }

    @Override
    public @Nullable F getFunction(@NotNull DyanasisFunctionSignature key) {
      var protectedFns = protectedFunctions();
      return protectedFns.containsKey(key) ? protectedFns.get(key) : registry.get(key);
    }

    @Override
    public @Unmodifiable @NotNull Map<DyanasisFunctionSignature, ? extends F> allFunctions() {
      Map<DyanasisFunctionSignature, F> fns = new HashMap<>();
      fns.putAll(registry);
      fns.putAll(protectedFunctions());
      return fns;
    }

    protected @NotNull Map<DyanasisFunctionSignature, F> protectedFunctions() {
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

    public DefaultObjectFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    public abstract @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input);

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
    protected final Map<DyanasisFunctionSignature, Function<O, DyanasisObject.ObjectFunction>> functions;

    public DefaultObjectType(
        @NotNull DyanasisRuntime runtime,
        @NotNull DyanasisNamespace namespace,
        @NotNull String name,
        @NotNull Class<? extends O> clazz
    ) {
      this(runtime, namespace, name, clazz, new HashMap<>(), new HashMap<>());
    }

    public DefaultObjectType(
        @NotNull DyanasisRuntime runtime,
        @NotNull DyanasisNamespace namespace,
        @NotNull String name,
        @NotNull Class<? extends O> clazz,
        Map<String, Function<O, ObjectProperty>> propertyHandlers,
        Map<DyanasisFunctionSignature, Function<O, ObjectFunction>> functionHandlers
    ) {
      super(runtime, namespace, name, clazz);
      this.properties = propertyHandlers;
      this.functions = functionHandlers;
      addProperties();
      addFunctions();
    }

    /**
     * Adds property handlers on creating this object.
     */
    protected void addProperties() {
    }

    /**
     * Adds function handlers on creating this object.
     */
    protected void addFunctions() {
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
        Map<DyanasisFunctionSignature, ObjectFunction> fns = new HashMap<>();
        for (Map.Entry<DyanasisFunctionSignature, Function<O, ObjectFunction>> entry : DefaultObjectType.this.functions.entrySet()) {
          fns.put(entry.getKey(), entry.getValue().apply(object));
        }
        return fns;
      };
    }

  }

}

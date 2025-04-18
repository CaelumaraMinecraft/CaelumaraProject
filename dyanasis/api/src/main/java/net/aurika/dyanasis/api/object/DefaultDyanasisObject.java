package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.declaration.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.function.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceContainer;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceIdent;
import net.aurika.dyanasis.api.declaration.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class DefaultDyanasisObject<T, Compiler extends DyanasisCompiler> implements DyanasisObject {

  @SuppressWarnings({"unchecked", "rawtypes"})
  protected static <C extends DyanasisObject, T extends DyanasisType<C>> @NotNull T type(
      @NotNull DyanasisRuntime runtime,
      @NotNull DyanasisNamespaceIdent path,
      @NotNull String typename,
      Class<? extends C> clazz,
      Supplier<? extends T> whenCreate
  ) {
    Validate.Arg.notNull(runtime, "runtime");
    Validate.Arg.notNull(path, "path");
    Validate.Arg.notNull(typename, "typename");
    Validate.Arg.notNull(clazz, "clazz");
    Validate.Arg.notNull(whenCreate, "whenCreate");
    DyanasisType type;
    DyanasisNamespaceContainer namespaces = runtime.environment().namespaces();
    DyanasisNamespace ns = namespaces.foundOrCreate(path);
    if (ns.hasDyanasisType(typename)) {
      type = ns.findDyanasisType(typename);
      if (!type.clazz().isAssignableFrom(clazz)) {
        throw new IllegalStateException();
      }
    } else {
      type = whenCreate.get();
    }
    return (T) type;
  }

  protected T value;
  protected final @NotNull Compiler compiler;

  protected DefaultDyanasisObject(
      T value,
      @NotNull Compiler compiler
  ) {
    Validate.Arg.notNull(compiler, "compiler");
    this.value = value;
    this.compiler = compiler;
  }

  /**
   * Gets the compiler of this dyanasis object.
   *
   * @return the compiler
   */
  public @NotNull Compiler compiler() { return compiler; }

  @Override
  public abstract @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties();

  @Override
  public abstract @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions();

  @Override
  public abstract @NotNull DyanasisType<? extends DyanasisObject> dyanasisType();

  @Override
  public @NotNull T valueAsJava() { return value; }

  @Override
  public @NotNull DyanasisRuntime dyanasisRuntime() { return dyanasisType().dyanasisRuntime(); }  // delegate to type

  public static abstract class DefaultObjectPropertyContainer<P extends ObjectProperty> implements ObjectPropertyContainer<P> {

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
      return ownerType().instancePropertyHandler().members(owner());
    }

    protected @NotNull DyanasisType ownerType() {
      return owner().dyanasisType();
    }

    @Override
    public abstract @NotNull DyanasisObject owner();

  }

  public static abstract class DefaultObjectFunctionContainer implements ObjectFunctionContainer {

    // 拓展函数 (对于对象)
    protected final @NotNull Map<DyanasisFunctionSignature, ObjectFunction> registry;

    protected DefaultObjectFunctionContainer() {
      this(new HashMap<>());
    }

    protected DefaultObjectFunctionContainer(@NotNull Map<DyanasisFunctionSignature, ObjectFunction> registry) {
      super();
      Validate.Arg.notNull(registry, "registry");
      this.registry = registry;
    }

    @Override
    public boolean hasFunction(@NotNull DyanasisFunctionSignature key) {
      return protectedFunctions().containsKey(key) || registry.containsKey(key);
    }

    @Override
    public @Nullable ObjectFunction getFunction(@NotNull DyanasisFunctionSignature key) {
      @NotNull Map<DyanasisFunctionSignature, ObjectFunction> protectedFns = protectedFunctions();
      return protectedFns.containsKey(key) ? protectedFns.get(key) : registry.get(key);
    }

    @Override
    public @Unmodifiable @NotNull Map<DyanasisFunctionSignature, ObjectFunction> allFunctions() {
      Map<DyanasisFunctionSignature, ObjectFunction> fns = new HashMap<>();
      fns.putAll(registry);
      fns.putAll(protectedFunctions());
      return fns;
    }

    protected @NotNull Map<DyanasisFunctionSignature, ObjectFunction> protectedFunctions() {
      return ownerType().instanceFunctionHandler().members(owner());
    }

    protected @NotNull DyanasisType ownerType() {
      return owner().dyanasisType();
    }

    @Override
    public abstract @NotNull DyanasisObject owner();

  }

  public abstract static class DefaultObjectProperty extends AbstractDyanasisProperty implements ObjectProperty {

    public DefaultObjectProperty(@NamingContract.Member final @NotNull String name) {
      super(name);
    }

    @Override
    public abstract @NotNull DyanasisObject value();

    @Override
    public abstract @NotNull DyanasisObject owner();

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

  public abstract static class DefaultObjectFunction extends AbstractDyanasisFunction implements ObjectFunction {

    public DefaultObjectFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    @Override
    public abstract @NotNull DyanasisObject owner();

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

}

package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.constructor.DyanasisConstructor;
import net.aurika.dyanasis.api.declaration.constructor.DyanasisConstructorContainer;
import net.aurika.dyanasis.api.declaration.executable.parameter.DyanasisParameterList;
import net.aurika.dyanasis.api.declaration.function.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.member.DyanasisMember;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DefaultDyanasisType<O extends DyanasisObject> extends AbstractDyanasisType<O> {

  protected final @NotNull DefaultConstructorContainer constructors = new DefaultConstructorContainer();
  protected final @NotNull DefaultInstancePropertyHandler properties = new DefaultInstancePropertyHandler();
  protected final @NotNull DefaultInstanceFunctionHandler functions = new DefaultInstanceFunctionHandler();

  public DefaultDyanasisType(
      @NotNull DyanasisRuntime runtime,
      @NotNull DyanasisNamespace namespace,
      @NotNull String name,
      @NotNull Class<? extends O> clazz
  ) {
    super(runtime, namespace, name, clazz);
    addConstructors();
    addProperties();
    addFunctions();
  }

  /**
   * Adds constructors on creating this object.
   */
  protected void addConstructors() { }

  /**
   * Adds property handlers on creating this object.
   */
  protected void addProperties() { }

  /**
   * Adds function handlers on creating this object.
   */
  protected void addFunctions() { }

  @Override
  public @NotNull DyanasisConstructorContainer constructors() {
    return constructors;
  }

  @Override
  public @NotNull InstancePropertyHandler<O> instancePropertyHandler() {
    return properties;
  }

  @Override
  public @NotNull InstanceFunctionHandler<O> instanceFunctionHandler() {
    return functions;
  }

  protected class DefaultConstructorContainer implements DyanasisConstructorContainer {

    protected final @NotNull Map<DyanasisParameterList, DyanasisConstructor> constructors;

    protected DefaultConstructorContainer() {
      this(new HashMap<>());
    }

    protected DefaultConstructorContainer(@NotNull Map<DyanasisParameterList, DyanasisConstructor> constructors) {
      Validate.Arg.notNull(constructors, "constructors");
      this.constructors = constructors;
    }

    @Override
    public boolean hasConstructor(@NotNull DyanasisParameterList params) {
      return constructors.containsKey(params);
    }

    @Override
    public @Nullable DyanasisConstructor findConstructor(@NotNull DyanasisParameterList params) {
      return constructors.get(params);
    }

    @Override
    public @NotNull DyanasisConstructor getConstructor(@NotNull DyanasisParameterList params) throws IllegalStateException {
      if (constructors.containsKey(params)) {
        return constructors.get(params);
      } else {
        throw new IllegalStateException("No constructor found for parameters " + params);
      }
    }

    @Override
    public @NotNull DefaultDyanasisType<O> dyanasisType() {
      return DefaultDyanasisType.this;
    }

  }

  protected class DefaultInstancePropertyHandler extends DefaultInstanceMemberHandler<String, DyanasisObject.ObjectProperty> implements InstancePropertyHandler<O> {

    protected DefaultInstancePropertyHandler() {
      this(new HashMap<>(), new HashMap<>());
    }

    protected DefaultInstancePropertyHandler(
        @NotNull Map<String, Function<O, DyanasisObject.ObjectProperty>> protectedRegistry,
        @NotNull Map<String, Function<O, DyanasisObject.ObjectProperty>> extendedRegistry
    ) {
      super(protectedRegistry, extendedRegistry);
    }

  }

  protected class DefaultInstanceFunctionHandler extends DefaultInstanceMemberHandler<DyanasisFunctionSignature, DyanasisObject.ObjectFunction> implements InstanceFunctionHandler<O> {

    protected DefaultInstanceFunctionHandler() {
      this(new HashMap<>(), new HashMap<>());
    }

    protected DefaultInstanceFunctionHandler(
        @NotNull Map<DyanasisFunctionSignature, Function<O, DyanasisObject.ObjectFunction>> protectedRegistry,
        @NotNull Map<DyanasisFunctionSignature, Function<O, DyanasisObject.ObjectFunction>> extendedRegistry
    ) {
      super(protectedRegistry, extendedRegistry);
    }

  }

  protected class DefaultInstanceMemberHandler<K, M extends DyanasisMember> implements InstanceMemberHandler<O, K, M> {

    protected final @NotNull Map<K, Function<O, M>> protectedRegistry;
    protected final @NotNull Map<K, Function<O, M>> extendedRegistry;

    protected DefaultInstanceMemberHandler(@NotNull Map<K, Function<O, M>> protectedRegistry, @NotNull Map<K, Function<O, M>> extendedRegistry) {
      Validate.Arg.notNull(protectedRegistry, "protectedRegistry");
      Validate.Arg.notNull(extendedRegistry, "extendedRegistry");
      this.protectedRegistry = protectedRegistry;
      this.extendedRegistry = extendedRegistry;
    }

    @Override
    public @NotNull Map<K, ? extends M> members(@NotNull O object) {
      Validate.Arg.notNull(object, "object");
      Map<K, M> mems = new HashMap<>();
      for (Map.Entry<K, Function<O, M>> entry : extendedRegistry.entrySet()) {
        mems.put(entry.getKey(), entry.getValue().apply(object));
      }
      for (Map.Entry<K, Function<O, M>> entry : protectedRegistry.entrySet()) {
        mems.put(entry.getKey(), entry.getValue().apply(object));
      }
      return mems;
    }

    @Override
    public @NotNull Map<K, ? extends M> protectedMembers(@NotNull O object) {
      Validate.Arg.notNull(object, "object");
      Map<K, M> mems = new HashMap<>();
      for (Map.Entry<K, Function<O, M>> entry : protectedRegistry.entrySet()) {
        mems.put(entry.getKey(), entry.getValue().apply(object));
      }
      return mems;
    }

    @Override
    public @NotNull Map<K, ? extends M> extendedMembers(@NotNull O object) {
      Validate.Arg.notNull(object, "object");
      Map<K, M> mems = new HashMap<>();
      for (Map.Entry<K, Function<O, M>> entry : extendedRegistry.entrySet()) {
        mems.put(entry.getKey(), entry.getValue().apply(object));
      }
      return mems;
    }

    @Override
    public void registerExtended(@NotNull K key, @NotNull Function<O, M> memberHandler) throws IllegalArgumentException {
      if (protectedRegistry.containsKey(key) || extendedRegistry.containsKey(key)) {
        throw new IllegalArgumentException("Member that keyed " + key + " is already registered");
      }
      extendedRegistry.put(key, memberHandler);
    }

  }

}

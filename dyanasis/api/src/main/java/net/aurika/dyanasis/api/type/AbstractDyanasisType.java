package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisType<O extends DyanasisObject> implements DyanasisType<O> {

  private final @NotNull DyanasisRuntime runtime;
  private final @NotNull DyanasisNamespace namespace;
  private final @NotNull String name;
  private final @NotNull Class<? extends O> clazz;

  public AbstractDyanasisType(
      @NotNull DyanasisRuntime runtime,
      @NotNull DyanasisNamespace namespace,
      @NotNull String name,
      @NotNull Class<? extends O> clazz
  ) {
    Validate.Arg.notNull(runtime, "runtime");
    Validate.Arg.notNull(namespace, "namespace");
    Validate.Arg.notNull(name, "name");
    Validate.Arg.notNull(clazz, "clazz");
    this.runtime = runtime;
    this.namespace = namespace;
    this.name = name;
    this.clazz = clazz;
    namespace.addDyanasisType(this);
  }

  @Override
  public @NotNull DyanasisTypeIdent ident() {
    return namespace.ident().mergeToTypeIdent(name);
  }

  @Override
  public boolean isInstance(DyanasisObject dyanasisObject) {
    return clazz.isInstance(dyanasisObject);
  }

  @Override
  public @NotNull DyanasisNamespace dyanasisNamespace() {
    return namespace;
  }

  @Override
  public @NotNull Class<? extends DyanasisObject> clazz() {
    return clazz;
  }

  @Override
  public abstract @NotNull InstancePropertyHandler<O> instancePropertyHandler();

  @Override
  public abstract @NotNull InstanceFunctionHandler<O> instanceFunctionHandler();

  @Override
  public @NotNull DyanasisRuntime dyanasisRuntime() {
    return runtime;
  }

}

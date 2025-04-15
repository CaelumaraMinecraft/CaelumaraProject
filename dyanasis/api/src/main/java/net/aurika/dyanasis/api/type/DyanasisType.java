package net.aurika.dyanasis.api.type;

import net.aurika.common.keyed.Keyed;
import net.aurika.dyanasis.api.declaration.constructor.DyanasisConstructor;
import net.aurika.dyanasis.api.declaration.constructor.DyanasisConstructorAnchor;
import net.aurika.dyanasis.api.declaration.constructor.DyanasisConstructorContainer;
import net.aurika.dyanasis.api.declaration.constructor.DyanasisConstructorsAware;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaced;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;

/**
 * The type of {@linkplain DyanasisObject}.
 *
 * @param <O> the type of object
 */
public interface DyanasisType<O extends DyanasisObject>
    extends Keyed<DyanasisTypeIdent>,
    DyanasisTypeAware,
    DyanasisNamespaced,
    DyanasisRuntimeObject,
    DyanasisConstructorsAware, DyanasisConstructorAnchor {

  @Override
  @NotNull DyanasisTypeIdent key();

  boolean isInstance(DyanasisObject dyanasisObject);

  @Override
  @NotNull DyanasisNamespace dyanasisNamespace();

  @NotNull Class<? extends O> clazz();

  @Override
  @NotNull DyanasisConstructorContainer constructors();

  @NotNull InstancePropertyHandler<O> instancePropertyHandler();

  @NotNull InstanceFunctionHandler<O> instanceFunctionHandler();

  @Override
  @NotNull DyanasisRuntime dyanasisRuntime();

  @Override
  default @NotNull DyanasisType<? extends O> dyanasisType() {
    return this;
  }

  interface TypeConstructor<T extends DyanasisType<?>> extends DyanasisConstructor {

    @Override
    @NotNull T owner();

  }

}

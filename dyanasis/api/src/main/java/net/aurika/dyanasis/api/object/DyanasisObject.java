package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAware;
import net.aurika.dyanasis.api.declaration.member.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.member.function.DyanasisFunctionAnchor;
import net.aurika.dyanasis.api.declaration.member.function.DyanasisFunctionsAware;
import net.aurika.dyanasis.api.declaration.member.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.member.property.DyanasisPropertiesAware;
import net.aurika.dyanasis.api.declaration.member.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.member.property.DyanasisPropertyAnchor;
import net.aurika.dyanasis.api.declaration.member.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeAware;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisObject
    extends DyanasisRuntimeObject,
    DyanasisPropertiesAware, DyanasisPropertyAnchor,
    DyanasisFunctionsAware, DyanasisFunctionAnchor,
    DyanasisDocAware, DyanasisDocAnchor,
    DyanasisTypeAware {

  @Override
  @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties();

  @Override
  @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions();

  @Override
  @Nullable ObjectDoc dyanasisDoc();

  /**
   * {@inheritDoc} Note that the return type must be {@code DyanasisType<|X|>},
   * the {@code |X|} is the implementation type of this dyanasis object.
   */
  @Override
  @NotNull DyanasisType<? extends DyanasisObject> dyanasisType();

  /**
   * 将这个 {@linkplain DyanasisObject} 与一个配置中的字符串相比较.
   *
   * @param cfgStr 配置字符串
   * @return 是否相同
   */
  boolean equals(@NotNull String cfgStr);

  /**
   * Gets the value as a java object.
   *
   * @return the value
   */
  @NotNull Object valueAsJava();

  @Override
  @NotNull DyanasisRuntime dyanasisRuntime();

  interface ObjectPropertyContainer<P extends ObjectProperty> extends DyanasisPropertyContainer<P>, NeedOwner {

    @Override
    @ApiStatus.Experimental
    @NotNull DyanasisObject owner();

  }

  interface ObjectFunctionContainer<F extends ObjectFunction> extends DyanasisFunctionContainer<F>, NeedOwner {

    @Override
    @ApiStatus.Experimental
    @NotNull DyanasisObject owner();

  }

  interface ObjectProperty extends DyanasisProperty {

    @Override
    @NotNull DyanasisObject owner();

    @Override
    default @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

  interface ObjectFunction extends DyanasisFunction {

    @Override
    @NotNull DyanasisObject owner();

    @Override
    default @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

  interface ObjectDoc extends DyanasisDoc {

    @Override
    @NotNull DyanasisObject owner();

    @Override
    default @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

}

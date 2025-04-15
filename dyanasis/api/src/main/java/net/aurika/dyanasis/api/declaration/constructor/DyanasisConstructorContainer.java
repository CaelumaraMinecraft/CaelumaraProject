package net.aurika.dyanasis.api.declaration.constructor;

import net.aurika.dyanasis.api.declaration.executable.parameter.DyanasisParameterList;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisConstructorContainer extends DyanasisTypeAware {

  boolean hasConstructor(@NotNull DyanasisParameterList params);

  @Nullable DyanasisConstructor findConstructor(@NotNull DyanasisParameterList params);

  @NotNull DyanasisConstructor getConstructor(@NotNull DyanasisParameterList params) throws IllegalStateException;

  @Override
  @NotNull DyanasisType<?> dyanasisType();

}

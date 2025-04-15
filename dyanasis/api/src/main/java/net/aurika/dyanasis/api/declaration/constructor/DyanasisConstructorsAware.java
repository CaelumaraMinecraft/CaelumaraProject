package net.aurika.dyanasis.api.declaration.constructor;

import org.jetbrains.annotations.NotNull;

public interface DyanasisConstructorsAware {

  @NotNull DyanasisConstructorContainer<?> constructors();

}

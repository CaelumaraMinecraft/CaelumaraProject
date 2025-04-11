package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface AccessProperty extends AccessMember, AccessDomain {

  @NotNull String propertyName();

  @Override
  @ApiStatus.NonExtendable
  default @NotNull String domainName() {
    return propertyName();
  }

  @NotNull DyanasisObject invoke();

  @Override
  @NotNull Expression operand();

}

package net.aurika.dyanasis.api.declaration.invokable.property;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.DyanasisInvokable;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.variable.DyanasisVariable;
import org.jetbrains.annotations.NotNull;

public interface DyanasisProperty extends DyanasisInvokable, DyanasisVariable {

  /**
   * Gets the dyanasis property name.
   *
   * @return the dyanasis property name
   */
  @Override
  @NamingContract.Invokable
  @NotNull String name();

  @Override
  @NotNull DyanasisPropertyAnchor owner();

  @Override
  @NotNull DyanasisObject value();

  static boolean isGetable(@NotNull DyanasisProperty property) {
    return property instanceof DyanasisGetableProperty;
  }

  static boolean isSetable(@NotNull DyanasisProperty property) {
    return property instanceof DyanasisSetableProperty;
  }

}

package net.aurika.dyanasis.api.declaration.property;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.member.DyanasisMember;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.variable.DyanasisVariable;
import org.jetbrains.annotations.NotNull;

public interface DyanasisProperty extends DyanasisMember, DyanasisVariable {

  /**
   * Gets the dyanasis property name.
   *
   * @return the dyanasis property name
   */
  @Override
  @NamingContract.Member
  @NotNull String name();

  @Override
  @NotNull DyanasisPropertyAnchor owner();

  @Override
  @NotNull DyanasisObject value();

  static boolean isGettable(@NotNull DyanasisProperty property) { return property instanceof DyanasisGettableProperty; }

  static boolean isSettable(@NotNull DyanasisProperty property) { return property instanceof DyanasisSettableProperty; }

}

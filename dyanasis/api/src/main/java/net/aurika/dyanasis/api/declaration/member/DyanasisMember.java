package net.aurika.dyanasis.api.declaration.member;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.access.DyanasisAccessible;
import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.property.DyanasisProperty;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a member of a dyanasis thing. It's function and property.
 *
 * @see DyanasisFunction
 * @see DyanasisProperty
 */
public interface DyanasisMember extends DyanasisDeclaration,
    DyanasisDocAnchor, NeedOwner, Named,
    DyanasisRuntimeObject, DyanasisAccessible {

  /**
   * Gets the name of the dyanasis member.
   *
   * @return the name
   */
  @Override
  @NamingContract.Member
  @NotNull String name();

  @Override
  @NotNull DyanasisMemberAnchor owner();

  @Override
  @NotNull DyanasisRuntime dyanasisRuntime();

}

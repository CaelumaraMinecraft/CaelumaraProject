package net.aurika.dyanasis.api.declaration.property;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisProperty implements DyanasisProperty {

  @NamingContract.Member
  private final @NotNull String name;

  public AbstractDyanasisProperty(@NamingContract.Member final @NotNull String name) {
    Validate.Arg.notNull(name, "name");
    this.name = name;
  }

  @Override
  @NamingContract.Member
  public @NotNull String name() {
    return name;
  }

  @Override
  public abstract @NotNull DyanasisPropertyAnchor owner();

  @Override
  public abstract @NotNull DyanasisObject value();

}

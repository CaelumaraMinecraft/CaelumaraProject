package net.aurika.dyanasis.api.declaration.invokable.property;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisProperty implements DyanasisProperty {

  @NamingContract.Invokable
  private final @NotNull String name;

  public AbstractDyanasisProperty(@NamingContract.Invokable final @NotNull String name) {
    Validate.Arg.notNull(name, "name");
    this.name = name;
  }

  @Override
  @NamingContract.Invokable
  public @NotNull String name() {
    return name;
  }

  @Override
  public abstract @NotNull DyanasisPropertyAnchor owner();

  @Override
  public abstract @NotNull DyanasisObject value();

}

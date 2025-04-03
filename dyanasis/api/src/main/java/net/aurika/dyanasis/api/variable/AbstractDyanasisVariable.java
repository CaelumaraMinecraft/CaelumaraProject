package net.aurika.dyanasis.api.variable;

import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class AbstractDyanasisVariable implements DyanasisVariable {

  private final @NotNull DyanasisObject value;

  public AbstractDyanasisVariable(@NotNull DyanasisObject value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public @NotNull DyanasisObject value() {
    return value;
  }

}

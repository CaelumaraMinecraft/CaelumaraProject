package net.aurika.common.data.provider;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

final class DirectDynamicSection implements DynamicSection {

  private final @NotNull SectionableDataSetter setter;

  public DirectDynamicSection(@NotNull SectionableDataSetter setter) {
    Validate.Arg.notNull(setter, "setter");
    this.setter = setter;
  }

  public @NotNull SectionableDataSetter setter() { return this.setter; }

  public void close() { }

}

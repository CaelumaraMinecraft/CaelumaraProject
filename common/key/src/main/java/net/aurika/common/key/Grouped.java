package net.aurika.common.key;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public interface Grouped extends GroupAware {

  @NotNull Group group();

}

class GroupedImpl implements Grouped {

  private final @NotNull Group group;

  GroupedImpl(@NotNull Group group) {
    Validate.Arg.notNull(group, "group");
    this.group = group;
  }

  @Override
  public @NotNull Group group() {
    return group;
  }

}

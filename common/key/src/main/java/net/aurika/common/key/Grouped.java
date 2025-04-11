package net.aurika.common.key;

import org.jetbrains.annotations.NotNull;

public interface Grouped extends GroupAware {

  @NotNull Group group();

}

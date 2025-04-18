package net.aurika.common.ident;

import org.jetbrains.annotations.NotNull;

public interface Grouped extends GroupAware {

  @NotNull Group group();

}

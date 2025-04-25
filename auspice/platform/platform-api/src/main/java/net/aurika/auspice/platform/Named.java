package net.aurika.auspice.platform;

import org.jetbrains.annotations.NotNull;

public interface Named extends MayHasName {

  @Override
  @NotNull String name();

}

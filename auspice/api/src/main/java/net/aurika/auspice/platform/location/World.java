package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface World {

  @NotNull String getName();

  @NotNull UUID getUID();

  int getMaxHeight();

  int getMinHeight();

}
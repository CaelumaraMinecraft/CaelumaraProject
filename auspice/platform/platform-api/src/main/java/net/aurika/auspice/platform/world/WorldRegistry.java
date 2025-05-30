package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface WorldRegistry {

  @NotNull List<? extends World> getWorlds();

  @Nullable World getWorld(@NotNull String name);

  @Nullable World getWorld(@NotNull UUID uuid);

}
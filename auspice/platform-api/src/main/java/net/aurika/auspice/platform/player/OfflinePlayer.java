package net.aurika.auspice.platform.player;

import net.aurika.auspice.platform.permission.ServerOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface OfflinePlayer extends ServerOperator {

  @NotNull UUID getUniqueId();

  @Nullable String getName();

}

package net.aurika.auspice.server.player;

import net.aurika.auspice.server.permission.ServerOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface OfflinePlayer extends ServerOperator {

  @NotNull UUID getUniqueId();

  @Nullable String getName();

}

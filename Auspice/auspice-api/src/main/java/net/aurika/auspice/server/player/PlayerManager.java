package net.aurika.auspice.server.player;

import net.aurika.auspice.checkerframework.annotations.SyncedData;
import net.aurika.auspice.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface PlayerManager {

  @SyncedData
  @NotNull Collection<? extends Player> getOnlinePlayers();

  @NotNull OfflinePlayer getOfflinePlayer(@NotNull UUID id);

  @NotNull Set<? extends OfflinePlayer> getOperators();

  @Nullable Player getPlayer(@NotNull UUID id);

  @Nullable Player getPlayer(@NotNull String name);

}

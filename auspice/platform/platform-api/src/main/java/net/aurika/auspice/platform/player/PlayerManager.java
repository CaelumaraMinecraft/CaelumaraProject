package net.aurika.auspice.platform.player;

import net.aurika.auspice.platform.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface PlayerManager {

  @NotNull Collection<? extends Player> getOnlinePlayers();

  @NotNull OfflinePlayer getOfflinePlayer(@NotNull UUID id);

  @NotNull Set<? extends OfflinePlayer> getOperators();

  @Nullable Player getPlayer(@NotNull UUID id);

  @Nullable Player getPlayer(@NotNull String name);

}

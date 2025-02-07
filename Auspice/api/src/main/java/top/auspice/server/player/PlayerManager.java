package top.auspice.server.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.checkerframework.annotations.SyncedData;
import top.auspice.server.entity.Player;

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

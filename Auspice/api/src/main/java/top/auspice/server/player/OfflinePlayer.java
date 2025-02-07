package top.auspice.server.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.server.permissions.ServerOperator;

import java.util.UUID;

public interface OfflinePlayer extends ServerOperator {

    @NotNull UUID getUniqueId();
    @Nullable String getName();

}

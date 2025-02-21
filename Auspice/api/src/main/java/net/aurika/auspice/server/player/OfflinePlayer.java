package net.aurika.auspice.server.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.server.permission.ServerOperator;

import java.util.UUID;

public interface OfflinePlayer extends ServerOperator {

    @NotNull UUID getUniqueId();
    @Nullable String getName();

}

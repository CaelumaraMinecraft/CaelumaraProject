package net.aurika.auspice.platform.player;

import net.aurika.auspice.platform.permission.ServerOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a reference to a player identity and the data belonging to a
 * player that is stored on the disk and can, thus, be retrieved without the
 * player needing to be online.
 */
public interface OfflinePlayer extends ServerOperator {

  @Nullable String name();

  @NotNull UUID id();

}

package net.aurika.auspice.platform.player;

import net.aurika.auspice.platform.UUIDIdentified;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a reference to a player identity and the data belonging to a
 * player that is stored on the disk and can, thus, be retrieved without the
 * player needing to be online.
 */
public interface OfflinePlayer extends UUIDIdentified {

  @Override
  @NotNull UUID uniqueId();

  /**
   * Returns the name of this player
   * <p>
   * Names are no longer unique past a single game session. For persistent storage
   * it is recommended that you use {@link #uniqueId()} instead.
   *
   * @return Player name or null if we have not seen a name for this player yet
   */
  @Nullable String name();

  @Deprecated
  interface Adapter<AP extends OfflinePlayer, PP> extends net.aurika.auspice.platform.adapter.Adapter<AP, PP> { }

}

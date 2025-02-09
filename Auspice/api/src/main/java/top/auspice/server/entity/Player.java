package top.auspice.server.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.annotations.ImplDontThrowUnsupported;
import net.aurika.text.TextObject;
import top.auspice.server.player.OfflinePlayer;

public interface Player extends Entity, OfflinePlayer {
    @ImplDontThrowUnsupported
    default void sendMessage(@NotNull TextObject message) {
    }

    @ImplDontThrowUnsupported
    default void sendActionBar(TextObject message) {
    }

    @NotNull String getName();

    @Nullable Object getRealPlayer();

    @NotNull TextObject getDisplayName();

    /**
     * Sets the "friendly" name to display of this player.
     *
     * @param displayName the display name to set
     */
    void setDisplayName(@Nullable TextObject displayName);
}

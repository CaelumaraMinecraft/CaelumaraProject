package net.aurika.auspice.platform.entity;

import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.player.OfflinePlayer;
import net.aurika.common.annotation.ImplDontThrowUnsupported;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Player extends Entity, OfflinePlayer, CommandSender {

  @ImplDontThrowUnsupported
  default void sendMessage(@NotNull Component message) {
  }

  @ImplDontThrowUnsupported
  default void sendActionBar(Component message) {
  }

  @NotNull String getName();

  @Nullable Object getRealPlayer();

  @NotNull Component displayName();

  /**
   * Sets the "friendly" name to display of this player.
   *
   * @param displayName the display name to set
   */
  void displayName(@Nullable Component displayName);

}

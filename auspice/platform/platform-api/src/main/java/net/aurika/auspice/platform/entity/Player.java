package net.aurika.auspice.platform.entity;

import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.entity.abstraction.Entity;
import net.aurika.auspice.platform.player.OfflinePlayer;
import net.aurika.common.annotation.ImplDontThrowUnsupported;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Player extends Entity, OfflinePlayer, CommandSender, Identified {

  @Override
  default @NotNull net.kyori.adventure.identity.Identity identity() {
    return net.kyori.adventure.identity.Identity.identity(this.uniqueId());
  }

  @Override
  @NotNull String name();

  @Override
  @NotNull UUID uniqueId();

  @ImplDontThrowUnsupported
  default void sendMessage(@NotNull Component message) {
  }

  @ImplDontThrowUnsupported
  default void sendActionBar(Component message) {
  }

  @NotNull Component displayName();

  /**
   * Sets the "friendly" name to display of this player.
   *
   * @param displayName the display name to set
   */
  void displayName(@Nullable Component displayName);

  interface Adapter<AP extends Player, BP> extends
      Entity.Adapter<AP, BP>,
      OfflinePlayer.Adapter<AP, BP> { }

}

package net.aurika.auspice.platform.bukkit.api.player;

import net.aurika.auspice.platform.player.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface BukkitOfflinePlayer extends OfflinePlayer {

  @NotNull org.bukkit.OfflinePlayer bukkitObject();

  interface Adapter<AP extends BukkitOfflinePlayer, BP extends org.bukkit.OfflinePlayer> extends OfflinePlayer.Adapter<AP, BP> { }

}

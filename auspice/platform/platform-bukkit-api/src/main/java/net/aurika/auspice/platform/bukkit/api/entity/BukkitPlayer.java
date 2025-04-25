package net.aurika.auspice.platform.bukkit.api.entity;

import net.aurika.auspice.platform.bukkit.api.player.BukkitOfflinePlayer;
import net.aurika.auspice.platform.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface BukkitPlayer extends Player, BukkitEntity, BukkitOfflinePlayer {

  @Override
  @NotNull org.bukkit.entity.Player bukkitObject();

  interface Adapter<AP extends BukkitPlayer, BP extends org.bukkit.entity.Player> extends
      Player.Adapter<AP, BP>,
      BukkitEntity.Adapter<AP, BP>,
      BukkitOfflinePlayer.Adapter<AP, BP> { }

}

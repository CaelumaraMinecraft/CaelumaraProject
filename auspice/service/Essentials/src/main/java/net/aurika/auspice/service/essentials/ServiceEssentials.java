package net.aurika.auspice.service.essentials;

import com.earth2me.essentials.Essentials;
import net.aurika.auspice.service.api.BukkitService;
import net.aurika.auspice.service.api.BukkitServiceVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ServiceEssentials implements BukkitService, BukkitServiceVanish {

  private static Essentials ESS;

  @Override
  public boolean isVanished(Player player) {
    return getEss().getUser(player).isVanished();
  }

  @Override
  public boolean isInGodMode(Player player) {
    return getEss().getUser(player).isGodModeEnabled();
  }

  @Override
  public boolean isIgnoring(Player ignorant, UUID ignoring) {
    return getEss().getUser(ignorant).isIgnoredPlayer(getEss().getUser(ignoring));
  }

  public static @NotNull Essentials getEss() {
    if (ESS == null) {
      initEss();
    }

    return ESS;
  }

  public static void initEss() {
    ESS = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
    if (ESS == null) {
      throw new IllegalStateException(
          "The EssentialsX plugin is not running on your server. You should ensure the plugin is running before you called the \"getEss()\" method");
    }
  }

}

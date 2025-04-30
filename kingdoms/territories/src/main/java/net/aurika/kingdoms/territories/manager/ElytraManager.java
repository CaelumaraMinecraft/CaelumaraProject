package net.aurika.kingdoms.territories.manager;

import net.aurika.kingdoms.auspice.AuspiceAddon;
import net.aurika.kingdoms.territories.GroupExt;
import net.aurika.kingdoms.territories.config.PowerfulTerritoryLang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.events.lands.LandChangeEvent;

public class ElytraManager implements Listener {

  public static final ElytraManager INSTANCE = new ElytraManager();

  private ElytraManager() { }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerFlyPassLand(@NotNull LandChangeEvent event) {

    Player player = event.getPlayer();
    if (player.isGliding()) {
      KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(player);
      @Nullable Land toLand = event.getToLand();
      if (
          toLand != null
              && toLand.isClaimed()
              && (!GroupExt.ELYTRA.hasAttribute(toLand.getKingdom(), kingdomPlayer.getKingdom()))
      ) {
        player.setGliding(false);
        PowerfulTerritoryLang.POWERFUL_TERRITORY_ELYTRA_PROTECTION.sendError(player);
      }
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerToggleGlide(EntityToggleGlideEvent event) {
    Entity entity = event.getEntity();
    if (event.isGliding()) {
      if (entity instanceof Player player) {
        KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(player);
        Land land = Land.getLand(entity.getLocation());
        if (
            land != null
                && land.isClaimed()
                && (!GroupExt.ELYTRA.hasAttribute(land.getKingdom(), kingdomPlayer.getKingdom()))
        ) {
          Bukkit.getScheduler().runTaskLater(AuspiceAddon.get(), () -> player.setGliding(false), 1);
          PowerfulTerritoryLang.POWERFUL_TERRITORY_ELYTRA_PROTECTION.sendError(player);
        }
      }
    }
  }

}

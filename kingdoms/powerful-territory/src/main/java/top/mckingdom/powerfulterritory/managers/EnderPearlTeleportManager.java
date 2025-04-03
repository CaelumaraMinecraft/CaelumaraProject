package top.mckingdom.powerfulterritory.managers;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.upgradable.MiscUpgrade;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.KingdomsLang;
import top.mckingdom.powerfulterritory.util.GroupExt;

public class EnderPearlTeleportManager implements Listener {

  @EventHandler(ignoreCancelled = true)
  public final void onPearlTeleport(PlayerTeleportEvent event) {

    if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {

      Player player;
      KingdomPlayer kPlayer = KingdomPlayer.getKingdomPlayer(player = event.getPlayer());
      Land land;
      if ((land = Land.getLand(event.getTo())) != null && land.isClaimed()) {
        Kingdom kingdom;
        if ((kingdom = land.getKingdom()).getUpgradeLevel(MiscUpgrade.ANTI_TRAMPLE) >= 3) {
          Kingdom var5 = kPlayer.getKingdom();
          if (!GroupExt.ENDER_PEARL_TELEPORT.hasAttribute(kingdom, var5)) {
            event.setCancelled(true);
            ParticleDisplay.of(Particle.CLOUD).withCount(10).spawn(event.getTo());
            KingdomsLang.LANDS_ENDER_PEARL_PROTECTION.sendError(player);
          } else {
            event.setCancelled(false);
          }
        }
      }
    }
  }

}

package net.aurika.kingdoms.territories.managers;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import net.aurika.kingdoms.territories.GroupExt;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.upgradable.MiscUpgrade;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.KingdomsLang;

public final class EnderPearlTeleportManager implements Listener {

  public static final EnderPearlTeleportManager INSTANCE = new EnderPearlTeleportManager();

  private EnderPearlTeleportManager() { }

  @EventHandler(ignoreCancelled = true)
  public void onPearlTeleport(@NotNull PlayerTeleportEvent event) {
    if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
      Player player = event.getPlayer();
      KingdomPlayer kPlayer = KingdomPlayer.getKingdomPlayer(player);
      @Nullable Location to = event.getTo();
      if (to == null) { return; }
      Land land = Land.getLand(to);
      if (land != null && land.isClaimed()) {
        Kingdom kingdom = land.getKingdom();
        if (kingdom == null) { return; }
        if (kingdom.getUpgradeLevel(MiscUpgrade.ANTI_TRAMPLE) >= 3) {
          Kingdom var5 = kPlayer.getKingdom();
          if (!GroupExt.ENDER_PEARL_TELEPORT.hasAttribute(kingdom, var5)) {
            event.setCancelled(true);
            ParticleDisplay.of(XParticle.of(Particle.CLOUD)).withCount(10).spawn(event.getTo());
            KingdomsLang.LANDS_ENDER_PEARL_PROTECTION.sendError(player);
          } else {
            event.setCancelled(false);
          }
        }
      }
    }
  }

}

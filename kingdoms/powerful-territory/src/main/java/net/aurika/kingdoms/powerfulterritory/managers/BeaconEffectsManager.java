package net.aurika.kingdoms.powerfulterritory.managers;

import net.aurika.kingdoms.powerfulterritory.util.GroupExt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;

public class BeaconEffectsManager implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void whenPlayerInBeacon(EntityPotionEffectEvent event) {
    if (event.getCause() == EntityPotionEffectEvent.Cause.BEACON) {
      if (event.getEntity() instanceof Player) {
        KingdomPlayer player = KingdomPlayer.getKingdomPlayer((Player) event.getEntity());
        Land land = Land.getLand(event.getEntity().getLocation());
        if (land != null && land.isClaimed()) {
          Kingdom landKingdom = land.getKingdom();
          if (landKingdom != null) {
            if (!landKingdom.hasAttribute(player.getKingdom(), GroupExt.BEACON_EFFECTS)) {
              event.setCancelled(true);
            }
          }
        }
      }
    }
  }

}

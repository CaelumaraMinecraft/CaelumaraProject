package net.aurika.kingdoms.powerfulterritory.managers;

import net.aurika.kingdoms.powerfulterritory.util.GroupExt;
import org.bukkit.entity.Boat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.player.KingdomPlayer;

public final class BoatUseListener implements Listener {

  public static final BoatUseListener INSTANCE = new BoatUseListener();

  private BoatUseListener() { }

  @EventHandler
  public void onInteractBoat(@NotNull PlayerInteractEntityEvent event) {
    if (event.getRightClicked() instanceof Boat) {
      KingdomPlayer player = KingdomPlayer.getKingdomPlayer(event.getPlayer());
      if (!player.hasKingdom()) return;
      if (!player.hasPermission(GroupExt.PERMISSION_USE_BOATS)) {
        event.setCancelled(true);
        GroupExt.PERMISSION_USE_BOATS.sendDeniedMessage(event.getPlayer());
      }
    }
  }

}

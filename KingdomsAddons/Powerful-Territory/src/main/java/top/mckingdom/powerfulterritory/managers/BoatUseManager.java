package top.mckingdom.powerfulterritory.managers;

import org.bukkit.entity.Boat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.kingdoms.constants.player.KingdomPlayer;
import top.mckingdom.powerfulterritory.util.GroupExt;

public class BoatUseManager implements Listener {

    @EventHandler
    public void onInteractBoat(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Boat) {
            KingdomPlayer player = KingdomPlayer.getKingdomPlayer(event.getPlayer());
            assert player != null : "getKingdomPlayer(org.bukkit.OfflinePlayer) returned null";
            if (!player.hasKingdom()) return;
            if (!player.hasPermission(GroupExt.PERMISSION_USE_BOATS)) {
                event.setCancelled(true);
                GroupExt.PERMISSION_USE_BOATS.sendDeniedMessage(event.getPlayer());
            }
        }
    }
}

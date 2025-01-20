package top.mckingdom.powerful_territory.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.events.lands.LandChangeEvent;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.powerful_territory.GroupExt;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang;

public class ElytraManager implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onPlayerFlyBy(LandChangeEvent event) {

        Player player = event.getPlayer();
        if (player.isGliding()) {
            KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(player);
            Land land = event.getToLand();
            if (
                    land != null
                            && land.isClaimed()
                            && (!GroupExt.ELYTRA.hasAttribute(land.getKingdom(), kingdomPlayer.getKingdom()))
            ) {
                player.setGliding(false);
                PowerfulTerritoryLang.POWERFUL_TERRITORY_ELYTRA_PROTECTION.sendError(player);
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerFly(EntityToggleGlideEvent event) {
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

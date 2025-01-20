package top.mckingdom.powerful_territory.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerful_territory.constants.invade_protection.InvadeProtection;
import top.mckingdom.powerful_territory.data.InvadeProtections;


public class InvadeManager implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInvade(KingdomPreInvadeEvent event) {
        event.getInvasion().getAffectedLands().forEach((chunk) -> {
            InvadeProtection status = InvadeProtections.getInvadeProtection(chunk);
            if (status != null && status.isProtect(event)) {
                event.setCancelled(true);
                PowerfulTerritoryLang.POWERFUL_TERRITORY_INVADE_PROTECTION.sendError(event.getInvasion().getInvaderPlayer());
            }
        });
    }
}

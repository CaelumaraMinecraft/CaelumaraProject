package top.mckingdom.powerfulterritory.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import top.mckingdom.powerfulterritory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerfulterritory.constants.invade_protection.InvadeProtection;
import top.mckingdom.powerfulterritory.data.InvadeProtections;

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

package top.mckingdom.uninvade.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import top.mckingdom.uninvade.config.UninvadeAddonLang;
import top.mckingdom.uninvade.data.LandInvadeProtectionDataManager;

public class InvadeManager implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInvade(KingdomPreInvadeEvent event) {
        event.getInvasion().getAffectedLands().forEach((chunk) -> {
            if (LandInvadeProtectionDataManager.getInvadeProtectionStatus(chunk).isProtect(event)) {
                event.setCancelled(true);
                UninvadeAddonLang.INVADE_PROTECTION.sendError(event.getInvasion().getInvaderPlayer());
            }
        });
    }


}

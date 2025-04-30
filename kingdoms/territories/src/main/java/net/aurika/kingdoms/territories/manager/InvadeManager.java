package net.aurika.kingdoms.territories.manager;

import net.aurika.kingdoms.territories.config.PowerfulTerritoryLang;
import net.aurika.kingdoms.territories.constant.invade_protection.InvadeProtection;
import net.aurika.kingdoms.territories.data.InvadeProtections;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import org.kingdoms.managers.invasions.Invasion;

public class InvadeManager implements Listener {

  public static final InvadeManager INSTANCE = new InvadeManager();

  private InvadeManager() { }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerInvade(KingdomPreInvadeEvent event) {
    Invasion invasion = event.getInvasion();
    invasion.getAffectedLands().forEach((chunk) -> {
      InvadeProtection status = InvadeProtections.getInvadeProtection(chunk);
      if (status != null && status.isProtect(event)) {
        event.setCancelled(true);
        PowerfulTerritoryLang.POWERFUL_TERRITORY_INVADE_PROTECTION.sendError(event.getInvasion().getInvaderPlayer());
      }
    });
  }

}

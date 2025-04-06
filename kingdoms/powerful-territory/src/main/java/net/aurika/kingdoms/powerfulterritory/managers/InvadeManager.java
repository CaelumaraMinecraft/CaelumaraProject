package net.aurika.kingdoms.powerfulterritory.managers;

import net.aurika.kingdoms.powerfulterritory.configs.PowerfulTerritoryLang;
import net.aurika.kingdoms.powerfulterritory.constant.invade_protection.InvadeProtection;
import net.aurika.kingdoms.powerfulterritory.data.InvadeProtections;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import org.kingdoms.managers.invasions.Invasion;

public final class InvadeManager implements Listener {

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

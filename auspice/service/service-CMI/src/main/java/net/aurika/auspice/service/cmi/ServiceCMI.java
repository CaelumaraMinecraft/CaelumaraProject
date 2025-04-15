package net.aurika.auspice.service.cmi;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import net.aurika.auspice.service.api.BukkitServiceVanish;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class ServiceCMI implements BukkitServiceVanish {

  public boolean isVanished(Player player) {
    return isVanished(player.getUniqueId());
  }

  public boolean isVanished(UUID uniqueId) {
    return CMI.getInstance().getVanishManager().getAllVanished().contains(uniqueId);
  }

  private CMIUser getUser(Player player) {
    return CMI.getInstance().getPlayerManager().getUser(player);
  }

  public boolean isInGodMode(Player player) {
    // "This can return NULL in some rare situations, so perform NPE check."
    CMIUser user = getUser(player);
    try {
      return user != null && user.isGod();
    } catch (NoSuchMethodError ex) {
      return false;
    }
  }

  @Override
  public boolean isIgnoring(Player ignorant, UUID ignoring) {
    CMIUser user = getUser(ignorant);
    return user != null && user.isIgnoring(ignoring);
  }

}

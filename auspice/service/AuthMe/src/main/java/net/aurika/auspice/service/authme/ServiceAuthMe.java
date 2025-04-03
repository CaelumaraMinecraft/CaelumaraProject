package net.aurika.auspice.service.authme;

import fr.xephi.authme.api.v3.AuthMeApi;
import net.aurika.auspice.service.api.BukkitServiceAuth;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ServiceAuthMe implements BukkitServiceAuth {

  public boolean isAuthenticated(@NotNull Player player) {
    return AuthMeApi.getInstance().isAuthenticated(player);
  }

}

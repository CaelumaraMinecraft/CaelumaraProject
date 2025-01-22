package top.auspice.craftbukkit.services;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.entity.Player;
import top.auspice.bukkit.services.BukkitService;
import top.auspice.bukkit.services.ServiceAuth;

public final class ServiceAuthMe implements ServiceAuth, BukkitService {
    public boolean isAuthenticated(Player player) {
        return AuthMeApi.getInstance().isAuthenticated(player);
    }
}

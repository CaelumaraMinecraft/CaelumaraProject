package net.aurika.auspice.service.api;

import org.bukkit.entity.Player;

public interface BukkitServiceAuth extends BukkitService, ServiceAuth {
    /**
     * Gets a player is authenticated.
     *
     * @param player the player
     * @return is authenticated
     */
    boolean isAuthenticated(Player player);
}

package top.auspice.spigot.services.essentials;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.auspice.bukkit.services.BukkitService;
import top.auspice.bukkit.services.ServiceVanish;

import java.util.UUID;

public class ServiceEssentials implements BukkitService, ServiceVanish {

    private static Essentials ESS;

    public boolean isVanished(Player player) {
        return getEss().getUser(player).isVanished();
    }

    public boolean isInGodMode(Player player) {
        return getEss().getUser(player).isGodModeEnabled();
    }

    public boolean isIgnoring(Player ignorant, UUID ignoring) {
        return getEss().getUser(ignorant).isIgnoredPlayer(getEss().getUser(ignoring));
    }

    public static Essentials getEss() {
        if (ESS == null) {
            initEss();
        }

        return ESS;
    }

    public static void initEss() {
        ESS = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (ESS == null) {
            throw new IllegalStateException("The EssentialsX plugin is not running on your server. You should ensure the plugin is running before you called the \"getEss()\" method");
        }
    }
}

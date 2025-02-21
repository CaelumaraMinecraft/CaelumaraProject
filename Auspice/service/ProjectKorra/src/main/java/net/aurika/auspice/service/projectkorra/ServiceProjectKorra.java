package net.aurika.auspice.service.projectkorra;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.region.RegionProtectionBase;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;

public class ServiceProjectKorra {

    private final @NotNull BiPredicate<Player, Location> handler;

    private static KingdomsRegionProtector protection;

    public ServiceProjectKorra(@NotNull BiPredicate<Player, Location> handler) {
        Objects.requireNonNull(handler, "handler");
        this.handler = handler;
    }

    public final @NotNull BiPredicate<Player, Location> handler() {
        return this.handler;
    }

    public void enable() {
        if (protection == null) {
            protection = new KingdomsRegionProtector();
        }
    }

    public final void registerConfig() {
        FileConfiguration config = ConfigManager.defaultConfig.get();
        config.addDefault("Properties.RegionProtection.Auspice.Respect", true);
        config.addDefault("Properties.RegionProtection.Auspice.ProtectDuringInvasions", false);
    }

    public static boolean isEnabled() {
        return ConfigManager.defaultConfig.get().getBoolean("Properties.RegionProtection.Auspice.Respect");
    }

    public static boolean protectDuringInvasions() {
        return ConfigManager.getConfig().getBoolean("Properties.RegionProtection.Auspice.ProtectDuringInvasions");
    }

    public final class KingdomsRegionProtector extends RegionProtectionBase {
        public KingdomsRegionProtector() {
            super("Auspice", "Auspice.Respect");
        }

        public boolean isRegionProtectedReal(@NotNull Player player, @NotNull Location location, @Nullable CoreAbility ability, boolean isIgnite, boolean isExplosive) {
            Objects.requireNonNull(player, "player");
            Objects.requireNonNull(location, "location");
            return ServiceProjectKorra.this.handler().test(player, location);
        }
    }
}

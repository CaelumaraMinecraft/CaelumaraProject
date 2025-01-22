package top.auspice.craftbukkit.services.projectkorra;

import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.region.RegionProtectionBase;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiFunction;

public class ServiceProjectKorra {
    @NotNull
    private final BiFunction<Player, Location, Boolean> handler;
    @Nullable
    private static KingdomsRegionProtector protection;

    public ServiceProjectKorra(@NotNull BiFunction<Player, Location, Boolean> handler) {
        Objects.requireNonNull(handler);
        this.handler = handler;
    }

    @NotNull
    public final BiFunction<Player, Location, Boolean> getHandler() {
        return this.handler;
    }

    public void enable() {
        if (protection == null) {
            protection = new KingdomsRegionProtector();
        }

    }

    public final void registerConfig() {
        FileConfiguration config = ConfigManager.defaultConfig.get();
        config.addDefault("Properties.RegionProtection.Kingdoms.Respect", true);
        config.addDefault("Properties.RegionProtection.Kingdoms.ProtectDuringInvasions", false);
    }

    public static boolean isEnabled() {
        return ConfigManager.defaultConfig.get().getBoolean("Properties.RegionProtection.Kingdoms.Respect");
    }

    public static boolean protectDuringInvasions() {
        return ConfigManager.getConfig().getBoolean("Properties.RegionProtection.Kingdoms.ProtectDuringInvasions");
    }

    public final class KingdomsRegionProtector extends RegionProtectionBase {
        public KingdomsRegionProtector() {
            super("Kingdoms", "Kingdoms.Respect");
        }

        public boolean isRegionProtectedReal(@NotNull Player player, @NotNull Location location, @Nullable CoreAbility ability, boolean isIgnite, boolean isExplosive) {
            Objects.requireNonNull(player);
            Objects.requireNonNull(location);
            return ServiceProjectKorra.this.getHandler().apply(player, location);
        }
    }

}

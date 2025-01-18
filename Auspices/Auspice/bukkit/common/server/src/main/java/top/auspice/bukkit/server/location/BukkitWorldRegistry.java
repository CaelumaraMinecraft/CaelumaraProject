package top.auspice.bukkit.server.location;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.bukkit.server.core.BukkitServer;
import top.auspice.server.location.WorldRegistry;
import top.auspice.utils.nonnull.NonNullMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BukkitWorldRegistry implements WorldRegistry {
    private final BukkitServer server;
    protected final Map<org.bukkit.World, BukkitWorld> worldMapping = new NonNullMap<>();

    public BukkitWorldRegistry(BukkitServer server) {
        this.server = server;
    }

    public BukkitWorld getWorld(org.bukkit.World world) {
        if (world == null) return null;
        BukkitWorld w1 = this.worldMapping.get(world);
        if (w1 != null) return w1;
        BukkitWorld w2 = BukkitWorld.of(world);
        this.worldMapping.put(world, w2);
        return w2;
    }

    @Override
    public @NotNull List<? extends BukkitWorld> getWorlds() {
        ArrayList<BukkitWorld> result = new ArrayList<>();
        for (org.bukkit.World world : Bukkit.getWorlds()) {
            result.add(this.getWorld(world));
        }
        return result;
    }

    @Override
    public @Nullable BukkitWorld getWorld(@NotNull String name) {
        return this.getWorld(Bukkit.getWorld(name));
    }

    @Override
    public @Nullable BukkitWorld getWorld(@NotNull UUID id) {
        return this.getWorld(Bukkit.getWorld(id));
    }
}

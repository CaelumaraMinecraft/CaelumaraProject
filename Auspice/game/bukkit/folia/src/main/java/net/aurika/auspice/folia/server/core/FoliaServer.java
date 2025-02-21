package net.aurika.auspice.folia.server.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.server.location.WorldRegistry;
import net.aurika.auspice.server.player.PlayerManager;

public class FoliaServer extends BukkitServer implements Server {
    public FoliaServer(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean isMainThread() {
        return false;  //TODO
    }

    @Override
    public @NotNull FoliaEventHandler eventManager() {
        return null;
    }

    @Override
    public @NotNull WorldRegistry worldRegistry() {
        return null;
    }

    @Override
    public @NotNull PlayerManager playerManager() {
        return null;
    }
}

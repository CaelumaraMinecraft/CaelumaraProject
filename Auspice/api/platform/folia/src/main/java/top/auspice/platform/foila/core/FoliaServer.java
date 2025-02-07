package top.auspice.platform.foila.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.auspice.platform.bukkit.core.BukkitServer;
import top.auspice.server.core.Server;
import top.auspice.server.location.WorldRegistry;
import top.auspice.server.player.PlayerManager;

public class FoliaServer extends BukkitServer implements Server {
    public FoliaServer(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean isMainThread() {
        return false;  //TODO
    }

    @Override
    public @NotNull FoliaEventHandler getEventHandler() {
        return null;
    }

    @Override
    public @NotNull WorldRegistry getWorldRegistry() {
        return null;
    }

    @Override
    public @NotNull PlayerManager getPlayerManager() {
        return null;
    }
}

package top.auspice.server.core;

import org.jetbrains.annotations.NotNull;
import top.auspice.server.event.EventHandler;
import top.auspice.server.location.WorldRegistry;
import top.auspice.server.permissions.PermissionManager;
import top.auspice.server.player.PlayerManager;

import java.util.Objects;

public interface Server {
    static void init(Server instance) {
        ServerContainer.INSTANCE = instance;
    }

    static Server get() {
        Server server = ServerContainer.INSTANCE;
        if (server == null) {
            throw new IllegalStateException("Server instance not initiated yet");
        }
        return server;
    }

    int getTicks();

    boolean isMainThread();

    @NotNull EventHandler getEventHandler();

    @NotNull WorldRegistry getWorldRegistry();

    @NotNull PlayerManager getPlayerManager();

    @NotNull PermissionManager getPermissionManager();

    default void onStartup() {
    }

    default void onReady() {
    }

    default void onEnable() {
    }
}
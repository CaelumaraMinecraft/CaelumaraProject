package net.aurika.auspice.server.core;

/**
 * A server.
 */
public interface Server {
    static void init(Server instance) {
        ServerContainer.INSTANCE = instance;
    }

    /**
     * Gets the server instance.
     *
     * @return the server instance
     * @throws IllegalStateException when the server was not initialized or has other problems
     */
    static Server get() throws IllegalStateException {
        Server server = ServerContainer.INSTANCE;
        if (server == null) {
            throw new IllegalStateException("Server instance not initiated yet");
        }
        return server;
    }

    default void onStartup() {
    }

    default void onReady() {
    }

    default void onEnable() {
    }
}
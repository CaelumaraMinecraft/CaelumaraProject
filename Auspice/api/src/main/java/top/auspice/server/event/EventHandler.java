package top.auspice.server.event;

import org.jetbrains.annotations.NotNull;

public interface EventHandler {
    void callEvent(@NotNull Object event);

    void registerEvents(@NotNull Object listener);

    default void onLoad() {
    }
}

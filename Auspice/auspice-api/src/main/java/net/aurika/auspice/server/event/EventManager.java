package net.aurika.auspice.server.event;

import org.jetbrains.annotations.NotNull;

public interface EventManager {

  void callEvent(@NotNull Object event);

  void registerEvents(@NotNull Object listener);

  default void onLoad() {
  }

}

package net.aurika.auspice.platform.event;

import org.jetbrains.annotations.NotNull;

public interface EventManager {

  void callEvent(@NotNull Event event);

  void registerEventHandler(@NotNull Object listener);

  default void onLoad() {
  }

}

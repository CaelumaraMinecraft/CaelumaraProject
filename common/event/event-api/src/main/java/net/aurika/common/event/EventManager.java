package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

public interface EventManager {

  void callEvent(@NotNull Event event);

}

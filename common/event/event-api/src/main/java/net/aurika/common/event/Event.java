package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

public interface Event {

  @NotNull ListenerContainer<? extends Event> listeners();

}

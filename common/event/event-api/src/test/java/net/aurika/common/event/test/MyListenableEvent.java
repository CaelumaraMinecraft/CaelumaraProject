package net.aurika.common.event.test;

import net.aurika.common.event.Event;
import net.aurika.common.event.EventAPI;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.Emitter;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface MyListenableEvent extends Event {

  Emitter<MyListenableEvent> EMITTER = EventAPI.defaultEmitter(MyListenableEvent.class);

  default @NotNull Emitter<? extends MyListenableEvent> emitter() {
    return EMITTER;
  }

}

package net.aurika.common.event.test;

import net.aurika.common.event.Conduit;
import net.aurika.common.event.Event;
import net.aurika.common.event.EventAPI;
import net.aurika.common.event.Listenable;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface MyListenableEvent extends Event {

  Conduit<MyListenableEvent> CONDUIT = EventAPI.defaultConduit(MyListenableEvent.class);

  default @NotNull Conduit<? extends MyListenableEvent> emitter() {
    return CONDUIT;
  }

}

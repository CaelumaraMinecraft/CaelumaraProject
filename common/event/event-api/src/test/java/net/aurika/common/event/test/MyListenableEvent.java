package net.aurika.common.event.test;

import net.aurika.common.event.Event;
import net.aurika.common.event.EventAPI;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.Transformer;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface MyListenableEvent extends Event {

  Transformer<MyListenableEvent> TRANSFORMER = EventAPI.defaultTransformer(MyListenableEvent.class);

  default @NotNull Transformer<? extends MyListenableEvent> transformer() {
    return TRANSFORMER;
  }

}

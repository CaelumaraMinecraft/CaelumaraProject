package net.aurika.auspice.platform.event.player;

import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.platform.event.entity.EntityEvent;
import net.aurika.common.event.Event;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.Emitter;
import org.jetbrains.annotations.NotNull;

import static net.aurika.common.event.EventAPI.defaultEmitter;
import static net.aurika.common.event.EventAPI.delegateEmitter;

@Listenable
public interface PlayerEvent extends EntityEvent {

  Emitter<? extends PlayerEvent> EMITTER = delegateEmitter(defaultEmitter(PlayerEvent.class));

  @Override
  Player entity();

  @Override
  default @NotNull Emitter<? extends Event> emitter() {
    return EMITTER;
  }

}

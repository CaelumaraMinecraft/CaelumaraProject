package net.aurika.auspice.platform.event.player;

import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.platform.event.entity.EntityEvent;
import net.aurika.common.event.Event;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.Transformer;
import org.jetbrains.annotations.NotNull;

import static net.aurika.common.event.EventAPI.defaultTransformer;
import static net.aurika.common.event.EventAPI.delegateTransformer;

@Listenable
public interface PlayerEvent extends EntityEvent {

  Transformer<? extends PlayerEvent> TRANSFORMER = delegateTransformer(defaultTransformer(PlayerEvent.class));

  @Override
  Player entity();

  @Override
  default @NotNull Transformer<? extends Event> transformer() {
    return TRANSFORMER;
  }

}

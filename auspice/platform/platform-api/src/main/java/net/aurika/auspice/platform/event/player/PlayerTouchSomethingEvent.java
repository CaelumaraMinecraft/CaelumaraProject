package net.aurika.auspice.platform.event.player;

import net.aurika.common.event.Conduit;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.event.player.PlayerTouchSomethingEvent$Companion.EMITTER_CONTAINER;

/**
 * Called on player touches something, 这是一个很广义的概念, 比如玩家点火, 放置方块, 使用方块, 交互实体
 */
@Listenable
public interface PlayerTouchSomethingEvent extends PlayerEvent {

  static @NotNull Conduit<PlayerTouchSomethingEvent> emitter() {
    return EMITTER_CONTAINER.conduit();
  }

  static @NotNull Conduit<PlayerTouchSomethingEvent> replaceEmitter(@NotNull Conduit<PlayerTouchSomethingEvent> newConduit) {
    return EMITTER_CONTAINER.replaceConduit(newConduit);
  }

  @Override
  default @NotNull Conduit<? extends PlayerTouchSomethingEvent> eventConduit() {
    return emitter();
  }

}

final class PlayerTouchSomethingEvent$Companion {

  static final EmitterContainer<PlayerTouchSomethingEvent> EMITTER_CONTAINER = new EmitterContainer<>(
      PlayerTouchSomethingEvent.class);

}

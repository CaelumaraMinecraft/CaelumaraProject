package net.aurika.auspice.platform.event.player;

import net.aurika.common.event.Emitter;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.event.player.PlayerTouchSomethingEvent$Companion.EMITTER_CONTAINER;

/**
 * Called on player touches something, 这是一个很广义的概念, 比如玩家点火, 放置方块, 使用方块, 交互实体
 */
@Listenable
public interface PlayerTouchSomethingEvent extends PlayerEvent {

  static @NotNull Emitter<PlayerTouchSomethingEvent> emitter() {
    return EMITTER_CONTAINER.emitter();
  }

  static @NotNull Emitter<PlayerTouchSomethingEvent> replaceEmitter(@NotNull Emitter<PlayerTouchSomethingEvent> newEmitter) {
    return EMITTER_CONTAINER.replaceEmitter(newEmitter);
  }

  @Override
  default @NotNull Emitter<? extends PlayerTouchSomethingEvent> eventEmitter() {
    return emitter();
  }

}

final class PlayerTouchSomethingEvent$Companion {

  static final EmitterContainer<PlayerTouchSomethingEvent> EMITTER_CONTAINER = new EmitterContainer<>(
      PlayerTouchSomethingEvent.class);

}

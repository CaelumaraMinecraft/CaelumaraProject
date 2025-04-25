package net.aurika.auspice.event.bukkit;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an event on Bukkit platform.
 */
@Listenable
public interface BukkitEvent extends MinecraftEvent {

  static @NotNull Emitter<BukkitEvent> emitter() { return BukkitEvent$Companion.EMITTER_CONTAINER.emitter(); }

  @EmitterReplaceMethod
  static @NotNull Emitter<BukkitEvent> replaceEmitter(@NotNull Emitter<BukkitEvent> newEmitter) {
    synchronized (BukkitEvent$Companion.EMITTER_CONTAINER) {
      return BukkitEvent$Companion.EMITTER_CONTAINER.replaceEmitter(newEmitter);
    }
  }

  /**
   * Try to cast this event to the bukkit event type. Such as get the delegated bukkit event object.
   *
   * @return the bukkit event type
   */
  @Nullable org.bukkit.event.Event toBukkitEvent();

  @Override
  default @NotNull Emitter<? extends BukkitEvent> eventEmitter() { return emitter(); }

}

final class BukkitEvent$Companion {

  static final EmitterContainer<BukkitEvent> EMITTER_CONTAINER = new EmitterContainer<>(BukkitEvent.class);

}

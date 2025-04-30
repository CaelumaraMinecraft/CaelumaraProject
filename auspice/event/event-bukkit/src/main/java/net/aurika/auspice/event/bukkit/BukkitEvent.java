package net.aurika.auspice.event.bukkit;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.common.event.Conduit;
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

  static @NotNull Conduit<BukkitEvent> emitter() { return BukkitEvent$Companion.EMITTER_CONTAINER.conduit(); }

  @EmitterReplaceMethod
  static @NotNull Conduit<BukkitEvent> replaceEmitter(@NotNull Conduit<BukkitEvent> newConduit) {
    synchronized (BukkitEvent$Companion.EMITTER_CONTAINER) {
      return BukkitEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
    }
  }

  /**
   * Try to cast this event to the bukkit event type. Such as get the delegated bukkit event object.
   *
   * @return the bukkit event type
   */
  @Nullable org.bukkit.event.Event toBukkitEvent();

  @Override
  default @NotNull Conduit<? extends BukkitEvent> eventConduit() { return emitter(); }

}

final class BukkitEvent$Companion {

  static final EmitterContainer<BukkitEvent> EMITTER_CONTAINER = new EmitterContainer<>(BukkitEvent.class);

}

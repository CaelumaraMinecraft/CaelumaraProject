package net.aurika.auspice.platform.event.weather;

import net.aurika.auspice.platform.event.world.WorldEvent;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.event.Emitter;
import net.aurika.common.event.EmitterReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface WeatherEvent extends WorldEvent {

  static @NotNull Emitter<WeatherEvent> emitter() { return WeatherEvent$Companion.EMITTER_CONTAINER.emitter(); }

  /**
   * @see net.aurika.auspice.platform.event.player.PlayerEvent#replaceEmitter(Emitter)
   */
  @EmitterReplaceMethod
  static @NotNull Emitter<WeatherEvent> replaceEmitter(@NotNull Emitter<WeatherEvent> newEmitter) {
    Validate.Arg.notNull(newEmitter, "newEmitter");
    synchronized (WeatherEvent$Companion.EMITTER_CONTAINER) {
      return WeatherEvent$Companion.EMITTER_CONTAINER.replaceEmitter(newEmitter);
    }
  }

  @Override
  World world();

}

final class WeatherEvent$Companion {

  static final EmitterContainer<WeatherEvent> EMITTER_CONTAINER = new EmitterContainer<>(WeatherEvent.class);

}

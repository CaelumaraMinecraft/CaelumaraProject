package net.aurika.auspice.platform.event.weather;

import net.aurika.auspice.platform.event.world.WorldEvent;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.ConduitReplaceMethod;
import net.aurika.common.event.Listenable;
import net.aurika.common.event.util.EmitterContainer;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

@Listenable
public interface WeatherEvent extends WorldEvent {

  static @NotNull Conduit<WeatherEvent> emitter() { return WeatherEvent$Companion.EMITTER_CONTAINER.conduit(); }

  /**
   * @see net.aurika.auspice.platform.event.player.PlayerEvent#replaceEmitter(Conduit)
   */
  @ConduitReplaceMethod
  static @NotNull Conduit<WeatherEvent> replaceEmitter(@NotNull Conduit<WeatherEvent> newConduit) {
    Validate.Arg.notNull(newConduit, "newEmitter");
    synchronized (WeatherEvent$Companion.EMITTER_CONTAINER) {
      return WeatherEvent$Companion.EMITTER_CONTAINER.replaceConduit(newConduit);
    }
  }

  @Override
  World world();

}

final class WeatherEvent$Companion {

  static final EmitterContainer<WeatherEvent> EMITTER_CONTAINER = new EmitterContainer<>(WeatherEvent.class);

}

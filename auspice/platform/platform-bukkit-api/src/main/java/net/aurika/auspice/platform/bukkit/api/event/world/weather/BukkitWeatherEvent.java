package net.aurika.auspice.platform.bukkit.api.event.world.weather;

import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import net.aurika.auspice.platform.bukkit.event.world.BukkitWorldEvent;
import net.aurika.auspice.platform.bukkit.world.BukkitWorld;
import net.aurika.auspice.platform.bukkit.world.BukkitWorldAdapter;
import org.bukkit.event.weather.WeatherEvent;

@NativeBukkitEvent(WeatherEvent.class)
public interface BukkitWeatherEvent extends BukkitWorldEvent {

  /**
   * @see WeatherEvent#getWorld()
   */
  @Override
  default BukkitWorld world() {
    org.bukkit.event.Event bukkitEvent = toBukkitEvent();
    if (bukkitEvent instanceof org.bukkit.event.weather.WeatherEvent) {
      org.bukkit.event.weather.WeatherEvent bukkitWeatherEvent = (org.bukkit.event.weather.WeatherEvent) bukkitEvent;
      return BukkitWorldAdapter.get().adapt(bukkitWeatherEvent.getWorld());
    }
    throw new UnsupportedOperationException();
  }

}

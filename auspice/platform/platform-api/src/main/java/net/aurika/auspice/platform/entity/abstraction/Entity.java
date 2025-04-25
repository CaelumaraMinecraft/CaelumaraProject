package net.aurika.auspice.platform.entity.abstraction;

import net.aurika.auspice.platform.UniqueIdentified;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.location.Location;
import net.aurika.auspice.platform.location.LocationAware;
import net.aurika.auspice.platform.location.LocationMutable;
import net.aurika.auspice.platform.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Entity extends CommandSender, UniqueIdentified, LocationAware {

  /**
   * Gets the unique id of the entity.
   *
   * @return the unique id
   */
  @Override
  @NotNull UUID uniqueId();

  @Override
  @NotNull World world();

  @Override
  double floatX();

  @Override
  double floatY();

  @Override
  double floatZ();

  @Override
  float pitch();

  @Override
  float yaw();

  @Override
  default @NotNull Location locationCopy() { return LocationAware.super.locationCopy(); }

  @Contract("_ -> param1")
  default LocationMutable joinLocation(@Nullable LocationMutable location) {
    return LocationAware.super.joinLocation(location);
  }

  interface Adapter<AE extends Entity, PE> extends net.aurika.auspice.platform.adapter.Adapter<AE, PE> { }

}

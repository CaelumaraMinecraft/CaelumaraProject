package net.aurika.auspice.platform.entity;

import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.location.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Entity extends CommandSender {

  @NotNull Location getLocationCopy();

  @Contract("_ -> param1")
  Location joinLocation(@Nullable Location location);

  @NotNull UUID getUniqueId();

}

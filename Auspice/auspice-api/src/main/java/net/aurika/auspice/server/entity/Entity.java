package net.aurika.auspice.server.entity;

import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.location.Location;
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

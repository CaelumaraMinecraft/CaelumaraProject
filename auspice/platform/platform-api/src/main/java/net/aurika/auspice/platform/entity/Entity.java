package net.aurika.auspice.platform.entity;

import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.location.PrecisionLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Entity extends CommandSender {

  @NotNull PrecisionLocation getLocationCopy();

  @Contract("_ -> param1")
  PrecisionLocation joinLocation(@Nullable PrecisionLocation location);

  @NotNull UUID getUniqueId();

}

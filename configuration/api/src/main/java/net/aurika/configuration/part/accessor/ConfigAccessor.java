package net.aurika.configuration.part.accessor;

import net.aurika.configuration.part.ConfigPart;
import net.aurika.configuration.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigAccessor {

  @NotNull ConfigEntry path();

  @NotNull ConfigAccessor gotoSub(@NotNull String name);

  @NotNull ConfigPart getConfig() throws ConfigAccessException;

}

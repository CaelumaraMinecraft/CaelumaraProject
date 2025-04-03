package net.aurika.config.part.accessor;

import net.aurika.config.part.ConfigPart;
import net.aurika.config.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigAccessor {

  @NotNull ConfigEntry path();

  @NotNull ConfigAccessor gotoSub(@NotNull String name);

  @NotNull ConfigPart getConfig() throws ConfigAccessException;

}

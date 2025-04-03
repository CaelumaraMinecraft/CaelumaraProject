package net.aurika.config.profile;

import net.aurika.config.part.ConfigPart;
import org.jetbrains.annotations.NotNull;

public interface Profile {

  @NotNull ProfileReference reference();

  @NotNull ConfigPart config();

}

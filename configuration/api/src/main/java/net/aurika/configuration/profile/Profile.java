package net.aurika.configuration.profile;

import net.aurika.configuration.part.ConfigPart;
import org.jetbrains.annotations.NotNull;

public interface Profile {

  @NotNull ProfileReference reference();

  @NotNull ConfigPart config();

}

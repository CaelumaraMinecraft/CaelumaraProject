package net.aurika.config.representer;

import net.aurika.config.part.ConfigPart;
import org.jetbrains.annotations.NotNull;

public interface Representer {

  @NotNull ConfigPart represent(Object data);

}

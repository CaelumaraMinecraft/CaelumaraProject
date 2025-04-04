package net.aurika.configuration.representer;

import net.aurika.configuration.part.ConfigPart;
import org.jetbrains.annotations.NotNull;

public interface Representer {

  @NotNull ConfigPart represent(Object data);

}

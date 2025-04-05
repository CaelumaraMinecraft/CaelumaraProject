package net.aurika.auspice.utils.debug;

import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface DebugEntry extends Keyed {

  @NotNull DebugKey key();

}

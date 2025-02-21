package net.aurika.auspice.utils.debug;

import net.aurika.common.key.Identified;
import org.jetbrains.annotations.NotNull;

public interface DebugEntry extends Identified {
    @NotNull DebugIdent ident();
}

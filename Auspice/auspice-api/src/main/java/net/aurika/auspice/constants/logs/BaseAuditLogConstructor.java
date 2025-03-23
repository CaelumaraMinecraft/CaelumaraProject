package net.aurika.auspice.constants.logs;

import net.aurika.common.key.Ident;
import net.aurika.common.key.Identified;
import org.jetbrains.annotations.NotNull;

public interface BaseAuditLogConstructor extends Identified {
    @NotNull Ident ident();
}

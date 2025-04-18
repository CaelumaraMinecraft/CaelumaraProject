package net.aurika.auspice.constants.logs;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import org.jetbrains.annotations.NotNull;

public interface BaseAuditLogConstructor extends Identified {

  @NotNull Ident ident();

}

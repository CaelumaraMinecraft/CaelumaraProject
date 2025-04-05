package net.aurika.auspice.constants.logs;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface BaseAuditLogConstructor extends Keyed {

  @NotNull Key key();

}

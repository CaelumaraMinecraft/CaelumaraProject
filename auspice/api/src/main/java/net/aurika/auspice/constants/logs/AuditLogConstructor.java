package net.aurika.auspice.constants.logs;

import net.aurika.common.key.Key;
import org.jetbrains.annotations.NotNull;

public abstract class AuditLogConstructor implements BaseAuditLogConstructor {

  private final @NotNull Key id;

  public AuditLogConstructor(@NotNull Key id) {
    this.id = id;
  }

  @Override
  public final @NotNull Key key() {
    return id;
  }

  public abstract AuditLog construct();

}

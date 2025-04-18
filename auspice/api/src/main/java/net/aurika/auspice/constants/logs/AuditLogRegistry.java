package net.aurika.auspice.constants.logs;

import net.aurika.common.ident.registry.AbstractIdentifiedRegistry;
import net.aurika.common.ident.registry.Lockable;
import org.jetbrains.annotations.NotNull;

public final class AuditLogRegistry extends AbstractIdentifiedRegistry<AuditLogConstructor> implements Lockable {

  private static boolean opening = true;

  public AuditLogRegistry() {
    super();
  }

  @Override
  public void register(@NotNull AuditLogConstructor logCtr) {
    if (opening) {
      super.register(logCtr);
    } else {
      throw new IllegalStateException(this.getClass().getSimpleName() + " is already closed");
    }
  }

  public void lock() {
    if (!opening) {
      throw new IllegalAccessError("Registers are already closed");
    } else {
      opening = false;
    }
  }

}
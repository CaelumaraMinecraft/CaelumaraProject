package net.aurika.auspice.constants.logs;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public interface Loggable {

  @NotNull LinkedList<AuditLog> logs();

  void log(@NotNull AuditLog log);

}

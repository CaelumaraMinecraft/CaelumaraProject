package top.auspice.constants.logs;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public interface Loggable {
    @NotNull
    LinkedList<AuditLog> getLogs();

    void log(@NotNull AuditLog log);
}

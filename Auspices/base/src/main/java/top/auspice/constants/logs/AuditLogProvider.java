package top.auspice.constants.logs;

import net.aurika.namespace.NSKeyed;

public interface AuditLogProvider extends NSKeyed {
    AuditLog construct();
}

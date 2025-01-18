package top.auspice.constants.logs;

import top.auspice.key.NSKeyed;

public interface AuditLogProvider extends NSKeyed {
    AuditLog construct();
}

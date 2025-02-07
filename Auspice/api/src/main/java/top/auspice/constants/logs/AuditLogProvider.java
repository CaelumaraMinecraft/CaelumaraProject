package top.auspice.constants.logs;

import net.aurika.namespace.NamespacedKeyContainer;

public interface AuditLogProvider extends NamespacedKeyContainer {
    AuditLog construct();
}

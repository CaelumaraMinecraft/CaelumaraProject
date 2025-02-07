package top.auspice.constants.logs;

import org.jetbrains.annotations.NotNull;
import net.aurika.namespace.Lockable;
import net.aurika.namespace.NSedKey;
import net.aurika.namespace.NSKedRegistry;
import top.auspice.main.Auspice;

import java.util.Map;

public final class AuditLogRegistry extends NSKedRegistry<AuditLogProvider> implements Lockable {
    private static boolean unlocked = true;

    public AuditLogRegistry() {
        super(Auspice.get(), "AUDIT_LOG");
    }

    public void register(@NotNull AuditLogProvider var1) {
        if (var1.getNamespacedKey().getNamespace().equals("Kingdoms")) {
            throw new IllegalArgumentException("Cannot register custom permission as kingdoms namespace: " + var1);
        } else {
            super.register(var1);
        }
    }

    public Map<NSedKey, AuditLogProvider> getRawRegistry() {
        return this.registered;
    }

    public void lock() {
        if (!unlocked) {
            throw new IllegalAccessError("Registers are already closed");
        } else {
            unlocked = false;
        }
    }
}
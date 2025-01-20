package top.mckingdom.civilizations.constants.civilization.permission;

import org.kingdoms.constants.namespace.Lockable;
import org.kingdoms.constants.namespace.NamespacedRegistry;

public class CivilizationPermissionRegistry extends NamespacedRegistry<CivilizationPermission> implements Lockable {
    public static boolean unLock = true;

    @Override
    public final void register(CivilizationPermission permission) {
            super.register(permission);
    }

    @Override
    public final void lock() {
        if (!unLock) {
            throw new IllegalAccessError("Registers are already closed");
        } else {
            unLock = false;
        }
    }
}

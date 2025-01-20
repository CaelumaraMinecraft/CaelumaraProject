package top.mckingdom.uninvade.data;

import org.kingdoms.constants.namespace.NamespaceRegistry;

public class InvadeProtectionRegistry extends NamespaceRegistry<InvadeProtection> {
    private static InvadeProtectionRegistry INSTANCE = new InvadeProtectionRegistry();

    public static InvadeProtectionRegistry get() {
        return INSTANCE;
    }

}

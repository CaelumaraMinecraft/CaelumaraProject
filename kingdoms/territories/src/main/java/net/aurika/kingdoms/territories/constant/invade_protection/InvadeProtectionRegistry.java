package net.aurika.kingdoms.territories.constant.invade_protection;

import org.kingdoms.constants.namespace.NamespacedRegistry;

public class InvadeProtectionRegistry extends NamespacedRegistry<InvadeProtection> {

  private static final InvadeProtectionRegistry INSTANCE = new InvadeProtectionRegistry();

  public static InvadeProtectionRegistry get() {
    return INSTANCE;
  }

}

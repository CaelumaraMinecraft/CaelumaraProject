package net.aurika.auspice.platform;

import net.aurika.common.ident.Group;

public final class Minecraft {

  public static final String NAMESPACE = "minecraft";
  public static final String GROUP_STRING = "net." + NAMESPACE;
  public static final Group GROUP = Group.group(GROUP_STRING);

  private Minecraft() { } // initialize

}

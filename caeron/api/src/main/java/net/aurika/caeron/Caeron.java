package net.aurika.caeron;

import net.aurika.common.ident.Group;

public final class Caeron {

  public static final String NAMESPACE = "caeron";
  public static final String GROUP_STRING = "net.aurika." + NAMESPACE;
  public static final Group GROUP = Group.group(GROUP_STRING);

  private Caeron() { }

}

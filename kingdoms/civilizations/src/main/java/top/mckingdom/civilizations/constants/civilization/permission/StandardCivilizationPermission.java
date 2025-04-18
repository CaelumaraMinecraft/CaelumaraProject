package top.mckingdom.civilizations.constants.civilization.permission;

import org.kingdoms.constants.namespace.Namespace;

import java.util.ArrayList;

public class StandardCivilizationPermission extends CivilizationPermission {

  public StandardCivilizationPermission(Namespace namespace, boolean b, Scope s) {
    super(namespace, b, s);
  }

  public static final CivilizationPermission BROADCAST_CIVILIZATION = build(
      "BROADCAST_CIVILIZATION", Scope.CIVILIZATION);
  public static final CivilizationPermission BROADCAST_SUBORDINATES = build(
      "BROADCAST_SUBORDINATES", Scope.SUBORDINATES);
  public static final CivilizationPermission MANAGE_FACILITIES = build("MANAGE_FACILITIES", Scope.SUBORDINATES);
  public static final CivilizationPermission PERMISSIONS = build("MANAGE_PERMISSIONS", Scope.SUBORDINATES);

  private static CivilizationPermission build(String key, Scope scope) {
    Namespace namespace = new Namespace("CivilizationsAddon", key);
    CivilizationPermission permission = new CivilizationPermission(namespace, true, scope);
    Companion.permissions.add(permission);
    permission.setHash(Companion.permissions.size());
    return permission;
  }

  private static CivilizationPermission build(String key, boolean b, Scope scope) {
    Namespace namespace = new Namespace("CivilizationsAddon", key);
    CivilizationPermission permission = new CivilizationPermission(namespace, b, scope);
    Companion.permissions.add(permission);
    permission.setHash(Companion.permissions.size());
    return permission;
  }

  private static class Companion {

    private static ArrayList<CivilizationPermission> permissions = new ArrayList<>();

  }

}

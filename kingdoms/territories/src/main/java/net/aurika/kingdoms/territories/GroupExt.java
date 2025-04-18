package net.aurika.kingdoms.territories;

import net.aurika.kingdoms.auspice.util.permission.XKingdomPermission;
import net.aurika.kingdoms.auspice.util.permission.XRelationAttribute;

import static net.aurika.kingdoms.auspice.configs.MsgConst.E_COLOR;
import static net.aurika.kingdoms.auspice.configs.MsgConst.S_COLOR;
import static net.aurika.kingdoms.territories.TerritoriesAddon.buildNS;

public final class GroupExt {

  public static final XRelationAttribute ELYTRA = XRelationAttribute.create(
      buildNS("ELYTRA"),
      "Elytra",
      S_COLOR + "Relation attribute to fly with elytra in other kingdoms."
  );
  public static final XRelationAttribute BEACON_EFFECTS = XRelationAttribute.create(
      buildNS("BEACON_EFFECTS"),
      "Beacon effects",
      S_COLOR + "Relation attribute to receive beacon effects in other kingdoms."
  );
  public static final XRelationAttribute ENDER_PEARL_TELEPORT = XRelationAttribute.create(
      buildNS("ENDER_PEARL_TELEPORT"),
      "Ender pearl teleport",
      S_COLOR + "Relation attribute to teleport to other kingdoms by ender pearl."
  );
  public static final XKingdomPermission PERMISSION_USE_BOATS = XKingdomPermission.create(
      buildNS("USE_BOATS"),
      "Use boats",
      S_COLOR + "Permission to use boats in constants.",
      E_COLOR + "You don't have permission to use boats."
  );
  public static final XKingdomPermission PERMISSION_MANAGE_LAND_CATEGORIES = XKingdomPermission.create(
      buildNS("MANAGE_LAND_CATEGORIES"),
      "Manage land categories",
      S_COLOR + "Permission to manage land categories in constants.",
      E_COLOR + "You don't have permission to manage land categories."
  );
  public static final XKingdomPermission PERMISSION_MANAGE_LAND_CONTRACTIONS = XKingdomPermission.create(
      buildNS("MANAGE_LAND_CONTRACTIONS"),
      "Manage land contractions",
      S_COLOR + "Permission to manage land contractions in kingdom's land.",
      E_COLOR + "You don't have permission to manage land contractions."
  );

  public static void init() {
    // <clinit>
  }

}

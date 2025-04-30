package net.aurika.kingdoms.auspice;

import net.aurika.kingdoms.auspice.util.permission.XKingdomPermission;
import net.aurika.kingdoms.auspice.util.permission.XRelationAttribute;

import static net.aurika.kingdoms.auspice.AuspiceAddon.buildNS;
import static net.aurika.kingdoms.auspice.configs.MsgConst.E_COLOR;
import static net.aurika.kingdoms.auspice.configs.MsgConst.S_COLOR;

public final class GroupExt {

  public static final XKingdomPermission PERMISSION_TRANSFER_MEMBERS = XKingdomPermission.create(
      buildNS("TRANSFER_MEMBERS"),
      "Transfer member",
      S_COLOR + "Permission to transfer member to other groups.",
      E_COLOR + "You don't have permission to transfer member."
  );

  public static final XRelationAttribute DIRECTLY_TRANSFER_MEMBERS = XRelationAttribute.create(
      buildNS("DIRECTLY_TRANSFER_MEMBERS"),
      "Directly transfer member",
      S_COLOR + "Relation attribute to transfer member to other group directly."
  );

  private GroupExt() {
  }

  public static void init() {
  }

}

package top.mckingdom.auspice.util;

import top.mckingdom.auspice.util.permission.XKingdomPermission;
import top.mckingdom.auspice.util.permission.XRelationAttribute;

import static top.mckingdom.auspice.AuspiceAddon.buildNS;
import static top.mckingdom.auspice.configs.MsgConst.E_COLOR;
import static top.mckingdom.auspice.configs.MsgConst.S_COLOR;

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

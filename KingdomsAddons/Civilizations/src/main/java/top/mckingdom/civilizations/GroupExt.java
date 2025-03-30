package top.mckingdom.civilizations;

import net.aurika.kingdoms.auspice.util.permission.XKingdomPermission;

import static net.aurika.kingdoms.auspice.configs.MsgConst.E_COLOR;
import static net.aurika.kingdoms.auspice.configs.MsgConst.S_COLOR;
import static top.mckingdom.civilizations.CivilizationsAddon.buildNS;

public final class GroupExt {

  public static final XKingdomPermission CIVILIZATION = XKingdomPermission.create(
      buildNS("CIVILIZATION"),
      "Civilization",
      S_COLOR + "Permissions of Civilization.",
      E_COLOR + "You don't have permission to manage civilization."
  );

  public static void init() {
    // <clinit>
  }

}

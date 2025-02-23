package top.mckingdom.auspice;

import org.kingdoms.constants.player.KingdomPermission;

public final class GroupExt {

    public static final KingdomPermission TEST_PERM = new KingdomPermission(AuspiceAddon.buildNS("TEST_PERM")) {
        {
            setHash(50);
        }
    };

    private GroupExt() {
    }

    public static void init() {
    }
}

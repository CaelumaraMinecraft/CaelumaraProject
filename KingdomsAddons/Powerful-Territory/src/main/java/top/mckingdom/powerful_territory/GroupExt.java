package top.mckingdom.powerful_territory;

import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import top.mckingdom.auspice.util.permission.KingdomPermissionRegister;
import top.mckingdom.auspice.util.permission.RelationAttributeRegister;
import top.mckingdom.auspice.util.permission.XKingdomPermission;

public class GroupExt {

    public static final RelationAttribute ELYTRA = RelationAttributeRegister.register("PowerfulTerritory", "ELYTRA");
    public static final RelationAttribute BEACON_EFFECTS = RelationAttributeRegister.register("PowerfulTerritory", "BEACON_EFFECTS");
    public static final RelationAttribute ENDER_PEARL_TELEPORT = RelationAttributeRegister.register("PowerfulTerritory", "ENDER_PEARL_TELEPORT");

    public static final XKingdomPermission PERMISSION_USE_BOATS = KingdomPermissionRegister.register("PowerfulTerritory", "USE_BOATS", "You don't have permission to use boats", "Permission to use boats in constants.");
    public static final XKingdomPermission PERMISSION_MANAGE_LAND_CATEGORIES = KingdomPermissionRegister.register("PowerfulTerritory", "MANAGE_LAND_CATEGORIES");
    public static final XKingdomPermission PERMISSION_MANAGE_LAND_CONTRACTIONS = KingdomPermissionRegister.register("PowerfulTerritory", "MANAGE_LAND_CONTRACTIONS");


    public static void init() {

    }
}

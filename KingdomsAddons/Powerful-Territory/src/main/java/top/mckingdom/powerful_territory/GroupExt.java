package top.mckingdom.powerful_territory;

import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import top.mckingdom.auspice.util.permission.XKingdomPermissionFactory;
import top.mckingdom.auspice.util.permission.XRelationAttributeFactory;
import top.mckingdom.auspice.util.permission.XKingdomPermission;

public class GroupExt {

    public static final RelationAttribute ELYTRA = XRelationAttributeFactory.register("PowerfulTerritory", "ELYTRA");
    public static final RelationAttribute BEACON_EFFECTS = XRelationAttributeFactory.register("PowerfulTerritory", "BEACON_EFFECTS");
    public static final RelationAttribute ENDER_PEARL_TELEPORT = XRelationAttributeFactory.register("PowerfulTerritory", "ENDER_PEARL_TELEPORT");

    public static final XKingdomPermission PERMISSION_USE_BOATS = XKingdomPermissionFactory.create("PowerfulTerritory", "USE_BOATS", "You don't have permission to use boats", "Permission to use boats in constants.");
    public static final XKingdomPermission PERMISSION_MANAGE_LAND_CATEGORIES = XKingdomPermissionFactory.create("PowerfulTerritory", "MANAGE_LAND_CATEGORIES");
    public static final XKingdomPermission PERMISSION_MANAGE_LAND_CONTRACTIONS = XKingdomPermissionFactory.create("PowerfulTerritory", "MANAGE_LAND_CONTRACTIONS");


    public static void init() {

    }
}

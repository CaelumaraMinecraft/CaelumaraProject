package top.mckingdom.auspice.util.permission;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.messenger.DefinedMessenger;
import top.mckingdom.auspice.util.MessengerUtil;

public final class KingdomPermissionRegister {

    private static int hashCount = 150;
    public static final XKingdomPermission PERMISSION_TRANSFER_MEMBERS = register("AuspiceAddon", "TRANSFER_MEMBERS");


    /**
     * 注册一个外交属性
     * @param namespace 你的插件的标识符,只包含大小写英文字母,建议驼峰命名法,首字母大写,比如"PeaceTreaties"
     * @param keyword 你要注册的王国权限的关键字,只能全部大写英文字母和下划线,比如"ENDER_PEARL_TELEPORT"
     * @return 你所注册的王国权限的一个对象
     */
    public static XKingdomPermission register(@org.intellij.lang.annotations.Pattern("[A-Za-z]{3,20}") @NonNull String namespace, @org.intellij.lang.annotations.Pattern("[A-Z0-9_]{3,100}") @NonNull String keyword) {
        return register(namespace, keyword, "{$e}You don't have permission " + keyword.toLowerCase() + "to do this.", "A kingdom permission: " + keyword);
    }

    public static XKingdomPermission register(String namespace, String keyword, String defaultMessage) {
        return register(namespace, keyword, defaultMessage, "A kingdom permission: " + keyword);
    }

//    public static XKingdomPermission register(String namespace, String keyword, String defaultLore) {
//        return register(namespace, keyword, "{$e}You don't have permission " + keyword.toLowerCase() + "to do this.", defaultLore);
//    }

    public static XKingdomPermission register(String namespace, String keyword, String defaultMessage, String defaultLore) {
        Namespace ns = new Namespace(namespace, keyword);
        DefinedMessenger m = MessengerUtil.createMessenger(new String[]{"permission", keyword}, defaultMessage);
        return register(ns, m, defaultMessage, defaultLore);
    }

    public static XKingdomPermission register(Namespace namespace, DefinedMessenger messenger, String defaultMessage, String defaultLore) {
        return XKingdomPermission.reg(namespace, messenger, defaultMessage, defaultLore, hashCount++) ;
    }

    public static void init() {

    }



}

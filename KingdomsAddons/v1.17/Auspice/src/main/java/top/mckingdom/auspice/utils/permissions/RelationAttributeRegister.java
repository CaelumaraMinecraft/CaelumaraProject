package top.mckingdom.auspice.utils.permissions;

import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.namespace.Namespace;

public class RelationAttributeRegister {

    private static int hashCount = 80;

    public static final RelationAttribute DIRECTLY_TRANSFER_MEMBERS = register("AuspiceAddon", "DIRECTLY_TRANSFER_MEMBERS");


    public static void init() {
    }


    public static XRelationAttribute register(String namespace, String keyword) {
        return register(namespace, keyword, "This is a relation attribute: " + keyword);
    }

    /**
     * 注册一个外交属性
     * @param namespace 你的插件的标识符,只包含大小写英文字母,建议驼峰命名法,首字母大写,比如"PeaceTreaties"
     * @param keyword 你要注册的外交属性的关键字,只能全部大写英文字母和下划线,比如"ENDER_PEARL_TELEPORT"
     * @return 你所注册的外交属性
     */
    public static XRelationAttribute register(String namespace, String keyword, String defaultLore) {
        Namespace ns = new Namespace(namespace, keyword);
        return register(ns, defaultLore);
    }

    public static XRelationAttribute register(Namespace namespace, String defaultLore) {
        return XRelationAttribute.reg(namespace, defaultLore, hashCount++);
    }



}

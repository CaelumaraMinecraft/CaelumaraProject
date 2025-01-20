package top.mckingdom.auspice.utils.permissions;

import org.kingdoms.constants.group.Group;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.group.model.relationships.StandardRelationAttribute;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.main.Kingdoms;

public class XRelationAttribute extends RelationAttribute {

    private final String defaultLore;
    public XRelationAttribute(Namespace namespace, String defaultLore) {
        super(namespace);
        this.defaultLore = defaultLore;
    }
    static XRelationAttribute reg(Namespace namespace, String defaultLore, int hash) {
        XRelationAttribute attr = new XRelationAttribute(namespace, defaultLore);
        attr.setHash(hash);
        Kingdoms.get().getRelationAttributeRegistry().register(attr);
        return attr;
    }
    @Override
    public boolean hasAttribute(Group group, Group group1) {
        return StandardRelationAttribute.hasAttribute(this, group, group1);
    }

    public boolean hasAttribute(Kingdom kingdom, Kingdom kingdom1) {
        return StandardRelationAttribute.hasAttribute(this, kingdom, kingdom1);
    }

    public String getDefaultLore() {
        return defaultLore;
    }
}

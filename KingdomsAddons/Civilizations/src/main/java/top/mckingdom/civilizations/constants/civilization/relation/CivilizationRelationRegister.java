package top.mckingdom.civilizations.constants.civilization.relation;

import org.kingdoms.constants.namespace.NamespacedRegistry;

public class CivilizationRelationRegister extends NamespacedRegistry<CivilizationRelation> {

    static final CivilizationRelationRegister INSTANCE = new CivilizationRelationRegister();

    public static CivilizationRelationRegister get() {
        return INSTANCE;
    }
}

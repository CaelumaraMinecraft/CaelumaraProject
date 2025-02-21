package top.mckingdom.civilizations.constants.civilization.relation;

import org.kingdoms.constants.namespace.Namespaced;

public interface CivilizationRelation extends Namespaced {
    /**
     * Gets this relation is can customize attributes by player.
     */
    boolean canCustomizeAttributes();
}

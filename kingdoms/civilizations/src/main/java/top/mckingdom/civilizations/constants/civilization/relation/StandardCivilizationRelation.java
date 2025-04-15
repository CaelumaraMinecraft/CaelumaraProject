package top.mckingdom.civilizations.constants.civilization.relation;

import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.civilizations.CivilizationsAddon;

public class StandardCivilizationRelation extends AbstractCivilizationRelation {

  public static final StandardCivilizationRelation SELF = b("SELF", false);
  public static final StandardCivilizationRelation NEUTRAL = b("NEUTRAL", true);

  private final boolean canCustomizeAttributes;

  private static StandardCivilizationRelation b(String key, boolean canCustomizeAttributes$default) {
    StandardCivilizationRelation relation = new StandardCivilizationRelation(CivilizationsAddon.buildNS(key), canCustomizeAttributes$default);
    CivilizationRelationRegister.get().register(relation);
    return relation;
  }

  public static void init() {
  }

  private StandardCivilizationRelation(Namespace namespace, boolean canCustomizeAttributes) {
    super(namespace);
    this.canCustomizeAttributes = canCustomizeAttributes;
  }

  @Override
  public boolean canCustomizeAttributes() {
    return this.canCustomizeAttributes;
  }

}

package top.mckingdom.civilizations.constants.civilization.member.types;

import org.kingdoms.constants.group.Nation;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import top.mckingdom.civilizations.CivilizationsAddon;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMemberType;
import top.mckingdom.civilizations.constants.civilization.member.objects.CivilizationMemberNation;

public class CivilizationMemberTypeNation extends CivilizationMemberType<Nation, CivilizationMember<Nation>> {
  public static final CivilizationMemberTypeNation INSTANCE = new CivilizationMemberTypeNation();

  protected CivilizationMemberTypeNation() {
    super(CivilizationsAddon.buildNS("NATION"), Nation.class);
  }

  @Override
  public CivilizationMemberNation deserializeFromIdentData(SectionableDataGetter section) {
    Nation n = Nation.getNation(section.get("key").asUUID());
    if (n != null) {
      return new CivilizationMemberNation(n);
    }
    return null;
  }

  @Override
  public CivilizationMemberNation of0(Nation nation) {
    return new CivilizationMemberNation(nation);
  }

  public static CivilizationMemberTypeNation get() {
    return INSTANCE;
  }

}

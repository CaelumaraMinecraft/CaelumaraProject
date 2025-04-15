package top.mckingdom.civilizations.constants.civilization.member.objects;

import org.kingdoms.constants.group.Nation;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.types.CivilizationMemberTypeNation;

public class CivilizationMemberNation extends CivilizationMember<Nation> {

  public CivilizationMemberNation(Nation nation) {
    super(nation, CivilizationMemberTypeNation.INSTANCE);
  }

  @Override
  public void serializeIndentData(SectionableDataSetter section) {
    section.get("key").setUUID(this.getKey().getKey());
  }

}

package top.mckingdom.civilizations.constants.civilization.member.objects;

import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.types.CivilizationMemberTypeKingdom;

public class CivilizationMemberKingdom extends CivilizationMember<Kingdom> {

    public CivilizationMemberKingdom(Kingdom kingdom) {
        super(kingdom, CivilizationMemberTypeKingdom.INSTANCE);
    }


    public void serializeIndentData(SectionableDataSetter setter) {
        setter.get("key").setUUID(this.getKey().getKey());
    }
}

package top.mckingdom.civilizations.constants.civilization.member.types;

import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import top.mckingdom.civilizations.CivilizationsAddon;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMemberType;
import top.mckingdom.civilizations.constants.civilization.member.objects.CivilizationMemberKingdom;

public class CivilizationMemberTypeKingdom extends CivilizationMemberType<Kingdom, CivilizationMember<Kingdom>> {

    public static final CivilizationMemberTypeKingdom INSTANCE = new CivilizationMemberTypeKingdom();
    protected CivilizationMemberTypeKingdom() {
        super(CivilizationsAddon.buildNS("KINGDOM"), Kingdom.class);
    }

    @Override
    public CivilizationMemberKingdom deserializeFromIdentData(SectionableDataGetter section) {
        Kingdom k = Kingdom.getKingdom(section.get("key").asUUID());
        if (k != null) {
            return new CivilizationMemberKingdom(k);
        }
        return null;
    }

    @Override
    public CivilizationMember<Kingdom> of0(Kingdom kingdom) {
        return new CivilizationMemberKingdom(kingdom);
    }

    public static CivilizationMemberTypeKingdom get() {
        return INSTANCE;
    }

}

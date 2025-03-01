package top.mckingdom.civilizations.constants.civilization.member;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.NamespacedRegistry;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import top.mckingdom.civilizations.constants.civilization.member.types.CivilizationMemberTypeKingdom;
import top.mckingdom.civilizations.constants.civilization.member.types.CivilizationMemberTypeNation;

public class CivilizationMemberTypeRegistry extends NamespacedRegistry<CivilizationMemberType<?, ?>> {

    public static final CivilizationMemberTypeRegistry INSTANCE = new CivilizationMemberTypeRegistry();

    public static CivilizationMemberTypeRegistry get() {
        return INSTANCE;
    }

    /**
     * @param section A section like: {@code
     *                {
     *                "type": String;
     *                "ident": {}
     *                }
     *                }
     */
    @Contract("_ -> new")
    public static MarkingCivilizationMember<?> deserializeMarkingMember(@NotNull SectionableDataGetter section) {
        Namespace typeNS = Namespace.fromString(section.get("type").asString());
        CivilizationMemberType<?, ?> type = CivilizationMemberTypeRegistry.get().getRegistered(typeNS);
        if (type == null) {
            throw new IllegalStateException("can not found civilization member type namespaced: '" + (section.get("type").asString()) + '\'');    //todo
        } else {
            return type.deserializeMarkingMember(section);
        }
    }

    /**
     * @param section A section like: {@code
     *                {
     *                "type": String;
     *                "ident": {};
     *                "subordinates": [];
     *                "metadata": {}
     *                }
     *                }
     */
    @Contract("_ -> new")
    public static CivilizationMember<?> deserializeIntactMember(@NotNull SectionableDataGetter section) {
        Namespace typeNS = Namespace.fromString(section.get("type").asString());
        CivilizationMemberType<?, ?> type = CivilizationMemberTypeRegistry.get().getRegistered(typeNS);
        if (type == null) {
            throw new IllegalStateException("can not found civilization member type namespaced: '" + (section.get("type").asString()) + '\'');    //todo
        } else {
            return type.deserializeIntactMember(section);
        }
    }

    public static void registerDefaults() {
        get().register(CivilizationMemberTypeNation.get());
        get().register(CivilizationMemberTypeKingdom.get());
    }
}

package top.mckingdom.civilizations.data.handlers;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.data.database.dataprovider.IdDataTypeHandler;
import org.kingdoms.data.database.dataprovider.SQLDataHandlerProperties;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import org.kingdoms.data.handlers.DataHandlerMetadata;
import org.kingdoms.data.handlers.abstraction.KeyedDataHandler;
import top.mckingdom.civilizations.constants.civilization.Civilization;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMemberTypeRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class dataHandlerCivilization extends KeyedDataHandler<UUID, Civilization> {
    public dataHandlerCivilization(IdDataTypeHandler<UUID> idDataTypeHandler, SQLDataHandlerProperties sqlDataHandlerProperties) {
        super(idDataTypeHandler, sqlDataHandlerProperties);
    }   //TODO

    @Override
    @NotNull
    public Civilization load(SectionableDataGetter rootSection, UUID uuid) {
        Set<CivilizationMember<?>> members = rootSection.get("members").asCollection(new HashSet<>(), (civilizationMembers, sectionableDataGetter) -> {
            civilizationMembers.add(CivilizationMemberTypeRegistry.deserializeIntactMember(sectionableDataGetter));
        });

        return null;
    }

    @Override
    public void save(SectionableDataSetter rootSection, Civilization civilization) {
        rootSection.get("members").setCollection(civilization.getTopMembers(), ((membersSection, member) -> {
            member.serialize(membersSection.createSection(), true);
        }));
        DataHandlerMetadata.serializeMetadata(rootSection, civilization);
    }
}

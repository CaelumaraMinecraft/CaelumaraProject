package net.aurika.data.handlers;

import org.jetbrains.annotations.NotNull;
import top.auspice.constants.player.AuspicePlayer;
import net.aurika.data.api.dataprovider.SQLDataHandlerProperties;
import net.aurika.data.api.dataprovider.SectionableDataGetter;
import net.aurika.data.api.dataprovider.SectionableDataSetter;
import net.aurika.data.api.dataprovider.StdIdDataType;
import net.aurika.data.api.handler.KeyedDataHandler;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityRegistry;
import net.aurika.namespace.NSedKey;

import java.util.UUID;

public class DataHandlerAuspicePlayer extends KeyedDataHandler<UUID, AuspicePlayer> {
    public static final DataHandlerAuspicePlayer INSTANCE = new DataHandlerAuspicePlayer();

    public DataHandlerAuspicePlayer() {
        super(StdIdDataType.UUID, new SQLDataHandlerProperties(new String[]{"diversity"}));  // TODO
    }

    @Override
    public void save(@NotNull SectionableDataSetter dataSetter, AuspicePlayer object) {
        Diversity lang = object.getDiversity();
        String langNs = lang.getNamespacedKey().asString();
        dataSetter.setString("lang", langNs);
    }

    @Override
    public @NotNull AuspicePlayer load(@NotNull SectionableDataGetter dataGetter, UUID key) {
        String langNs = dataGetter.getString("lang");
        Diversity lang = langNs == null ? Diversity.getDefault() : DiversityRegistry.INSTANCE.getRegistered(NSedKey.fromString(langNs));

        return new AuspicePlayer(key, lang);
    }
}

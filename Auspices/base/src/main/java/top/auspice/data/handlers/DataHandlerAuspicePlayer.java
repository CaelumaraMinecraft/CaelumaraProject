package top.auspice.data.handlers;

import org.jetbrains.annotations.NotNull;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.data.database.dataprovider.SQLDataHandlerProperties;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
import top.auspice.data.database.dataprovider.StdIdDataType;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityRegistry;
import top.auspice.key.NSedKey;

import java.util.UUID;

public class DataHandlerAuspicePlayer extends KeyedDataHandler<UUID, AuspicePlayer> {
    public DataHandlerAuspicePlayer() {
        super(StdIdDataType.UUID, new SQLDataHandlerProperties();
    }

    @Override
    public void save(@NotNull SectionableDataSetter dataSetter, AuspicePlayer object) {
        Diversity lang = object.getDiversity();
        String langNs = lang == null ? Diversity.getDefault().getNamespacedKey().asString() : lang.getNamespacedKey().asString();
        dataSetter.setString("lang", langNs);
    }

    @Override
    public @NotNull AuspicePlayer load(@NotNull SectionableDataGetter dataGetter, UUID key) {
        String langNs = dataGetter.getString("lang");
        Diversity lang = langNs == null ? Diversity.getDefault() : DiversityRegistry.INSTANCE.getRegistered(NSedKey.fromString(langNs));

        return new AuspicePlayer(key, lang);
    }
}

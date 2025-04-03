package net.aurika.auspice.data.handlers;

import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.diversity.DiversityRegistry;
import net.aurika.common.key.namespace.NSedKey;
import net.aurika.ecliptor.database.dataprovider.SQLDataHandlerProperties;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import net.aurika.ecliptor.database.dataprovider.StdIdDataType;
import net.aurika.ecliptor.handler.KeyedDataHandler;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DataHandlerAuspicePlayer extends KeyedDataHandler<UUID, AuspicePlayer> {

  public static final DataHandlerAuspicePlayer INSTANCE = new DataHandlerAuspicePlayer();

  public DataHandlerAuspicePlayer() {
    super(StdIdDataType.UUID, new SQLDataHandlerProperties(new String[]{"translation"}));  // TODO
  }

  @Override
  public void save(@NotNull SectionableDataSetter dataSetter, AuspicePlayer object) {
    Diversity lang = object.diversity();
    String langNs = lang.getNamespacedKey().asString();
    dataSetter.setString("lang", langNs);
  }

  @Override
  public @NotNull AuspicePlayer load(@NotNull SectionableDataGetter dataGetter, UUID key) {
    String langNs = dataGetter.getString("lang");
    Diversity lang = langNs == null ? Diversity.globalDefault() : DiversityRegistry.INSTANCE.getRegistered(
        NSedKey.fromString(langNs));

    return AuspicePlayer.Impl(key, lang);
  }

}

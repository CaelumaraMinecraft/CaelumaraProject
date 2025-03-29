package net.aurika.auspice.data.managers;

import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.auspice.data.handlers.DataHandlerAuspicePlayer;
import net.aurika.auspice.user.Auspice;
import net.aurika.ecliptor.centers.AurikaDataCenter;
import net.aurika.ecliptor.managers.base.KeyedDataManager;

import java.util.UUID;

public class AuspicePlayerManager extends KeyedDataManager<UUID, AuspicePlayer> {

  public AuspicePlayerManager(AurikaDataCenter dataCenter) {
    super(
        Auspice.createKey("data.manager.players"),
        dataCenter.constructDatabase(
            AuspiceGlobalConfig.DATABASE_TABLES_PLAYERS.getString(), "players",
            DataHandlerAuspicePlayer.INSTANCE
        ), false, dataCenter
    );
  }

}

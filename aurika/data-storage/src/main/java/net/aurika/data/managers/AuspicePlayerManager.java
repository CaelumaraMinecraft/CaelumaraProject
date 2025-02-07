package net.aurika.data.managers;

import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.constants.player.AuspicePlayer;
import net.aurika.data.centers.AurikaDataCenter;
import net.aurika.data.handlers.DataHandlerAuspicePlayer;
import net.aurika.data.managers.base.KeyedDataManager;
import top.auspice.main.Auspice;

import java.util.UUID;

public class AuspicePlayerManager extends KeyedDataManager<UUID, AuspicePlayer> {
    public AuspicePlayerManager(AurikaDataCenter dataCenter) {
        super(Auspice.namespacedKey("PLAYERS"), dataCenter.constructDatabase(AuspiceGlobalConfig.DATABASE_TABLES_PLAYERS.getString(), "players", DataHandlerAuspicePlayer.INSTANCE), false, dataCenter);
    }
}

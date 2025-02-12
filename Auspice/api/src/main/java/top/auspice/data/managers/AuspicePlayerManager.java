package top.auspice.data.managers;

import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.constants.player.AuspicePlayer;
import net.aurika.data.centers.AurikaDataCenter;
import top.auspice.data.handlers.DataHandlerAuspicePlayer;
import net.aurika.data.managers.base.KeyedDataManager;
import top.auspice.main.Auspice;

import java.util.UUID;

public class AuspicePlayerManager extends KeyedDataManager<UUID, AuspicePlayer> {
    public AuspicePlayerManager(AurikaDataCenter dataCenter) {
        super(Auspice.key_advtr("data.manager.players"), dataCenter.constructDatabase(AuspiceGlobalConfig.DATABASE_TABLES_PLAYERS.getString(), "players", DataHandlerAuspicePlayer.INSTANCE), false, dataCenter);
    }
}

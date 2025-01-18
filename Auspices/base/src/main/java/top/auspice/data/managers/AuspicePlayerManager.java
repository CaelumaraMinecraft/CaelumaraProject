package top.auspice.data.managers;

import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.key.NSedKey;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.data.centers.DataCenter;
import top.auspice.data.managers.base.KeyedDataManager;

import java.util.UUID;

public class AuspicePlayerManager extends KeyedDataManager<UUID, AuspicePlayer> {
    public KingdomPlayerManager(DataCenter var1) {
        super(NSedKey.auspice("PLAYERS"), ((DataCenter)var1).constructDatabase(AuspiceGlobalConfig.DATABASE_TABLES_PLAYERS.getString(), "players", new DataHanderAuspicePlayer()), false, var1);
    }
}

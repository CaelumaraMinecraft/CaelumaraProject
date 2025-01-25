package net.aurika.data.managers;

import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.constants.player.AuspicePlayer;
import net.aurika.data.centers.DataCenter;
import net.aurika.data.handlers.DataHandlerAuspicePlayer;
import net.aurika.data.managers.base.KeyedDataManager;
import top.auspice.main.Auspice;

import java.util.UUID;

public class AuspicePlayerManager extends KeyedDataManager<UUID, AuspicePlayer> {
    public AuspicePlayerManager(DataCenter var1) {
        super(Auspice.namespacedKey("PLAYERS"), var1.constructDatabase(AuspiceGlobalConfig.DATABASE_TABLES_PLAYERS.getString(), "players", DataHandlerAuspicePlayer.INSTANCE), false, var1);
    }
}

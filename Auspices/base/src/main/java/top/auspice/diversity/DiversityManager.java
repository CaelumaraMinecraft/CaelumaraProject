package top.auspice.diversity;

import top.auspice.constants.player.AuspicePlayer;
import top.auspice.server.player.OfflinePlayer;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;

import java.util.UUID;

public class DiversityManager {





    public static Diversity localeOf(UUID uuid) {
        return AuspicePlayer.getAuspicePlayer(uuid).getDiversity();
    }

    public static Diversity localeOf(Player player) {
        return AuspicePlayer.getAuspicePlayer(player).getDiversity();
    }

    public static Diversity localeOf(OfflinePlayer offlinePlayer) {
        return AuspicePlayer.getAuspicePlayer(offlinePlayer).getDiversity();
    }

    public static Diversity localeOf(CommandSender commandSender) {
        return commandSender instanceof Player ? localeOf((Player) commandSender) : getDefaultLanguage();
    }

    public static Diversity getDefaultLanguage() {
        return Diversity.getDefault();
    }

}

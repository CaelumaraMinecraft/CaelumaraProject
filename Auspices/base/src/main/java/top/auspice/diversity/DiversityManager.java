package top.auspice.diversity;

import net.aurika.namespace.NSedKey;
import net.aurika.namespace.NamespacedKeyContainer;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.server.player.OfflinePlayer;

import java.util.UUID;

public interface DiversityManager extends NamespacedKeyContainer {

    default @NotNull NSedKey getId() {
        return getNamespacedKey();
    }

    public static Diversity localeOf(UUID playerID) {
        return AuspicePlayer.getAuspicePlayer(playerID).getDiversity();
    }

    public static Diversity localeOf(Player player) {
        return AuspicePlayer.getAuspicePlayer(player).getDiversity();
    }

    public static Diversity localeOf(OfflinePlayer offlinePlayer) {
        return AuspicePlayer.getAuspicePlayer(offlinePlayer).getDiversity();
    }

    public static Diversity localeOf(CommandSender commandSender) {
        return commandSender instanceof Player ? localeOf((Player) commandSender) : getDefaultDiversity();
    }

    public static Diversity getDefaultDiversity() {
        return Diversity.getDefault();
    }
}

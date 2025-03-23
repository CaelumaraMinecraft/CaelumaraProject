package net.aurika.auspice.translation.message.manager;

import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.TranslationEntry;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;
import net.aurika.auspice.server.player.OfflinePlayer;
import net.aurika.common.key.Ident;
import net.aurika.common.key.Identified;
import net.aurika.common.key.registry.AbstractIdentifiedRegistry;
import net.aurika.common.key.registry.IdentifiedRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface MessageManager extends Identified {
    IdentifiedRegistry<MessageManager> MANAGER_REGISTRY = new AbstractIdentifiedRegistry<>() {
        @Override
        public void register(@NotNull MessageManager manager) {
            super.register(manager);
        }
    };

    @Override
    @NotNull Ident ident();

    /**
     * Gets a message from a translation and a translation entry.
     *
     * @param diversity the translation
     * @param entry the translation entry
     * @param useDefault weather or not use the default translation when cannot find translation from the specified translation and translation entry
     * @return the found translation
     * @throws IllegalStateException when cannot found default translation by the translation key
     */
    @Nullable MessageProvider getMessage(@NotNull Diversity diversity, @NotNull TranslationEntry entry, boolean useDefault);

    /**
     * Gets the default translation for this message manager.
     *
     * @return the default translation
     */
    @NotNull Diversity defaultDiversity();

    static void registerManager(@NotNull MessageManager manager) {
        MANAGER_REGISTRY.register(manager);
    }

    static @Nullable MessageManager getManager(@NotNull Ident managerID) {
        return MANAGER_REGISTRY.getRegistered(managerID);
    }

    static Diversity diversityOf(UUID playerID) {
        return AuspicePlayer.getAuspicePlayer(playerID).diversity();
    }

    static Diversity diversityOf(Player player) {
        return AuspicePlayer.getAuspicePlayer(player).diversity();
    }

    static Diversity diversityOf(OfflinePlayer offlinePlayer) {
        return AuspicePlayer.getAuspicePlayer(offlinePlayer).diversity();
    }

    static Diversity diversityOf(CommandSender commandSender) {
        return commandSender instanceof Player ? diversityOf((Player) commandSender) : getDefaultDiversity();
    }

    static Diversity getDefaultDiversity() {
        return Diversity.globalDefault();
    }
}

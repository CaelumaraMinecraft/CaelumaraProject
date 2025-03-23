package net.aurika.auspice.configs.messages;

import net.aurika.auspice.user.AuspiceUser;
import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;
import net.aurika.auspice.server.player.OfflinePlayer;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.messenger.DefinedMessenger;
import net.aurika.common.key.namespace.NSedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LanguageManager {

    protected static final Map<AuspiceUser, Map<NSedKey, DefinedMessenger[]>> registry = new LinkedHashMap<>(1);

    /**
     * 注册多条默认的消息
     * <p>
     * 注册的消息用于当在对应的语言中找不到对应的消息时, 将会使用该注册的消息
     *
     * @param provider        这几条消息的提供者
     * @param messengersClass
     * @param <T>             提供多条默认消息的枚举类
     */
    public static <T extends Enum<?> & DefinedMessenger> void registerDefaultMessages(AuspiceUser provider, NSedKey group, Class<T> messengersClass) {
        Objects.requireNonNull(provider, "Messengers provider cannot be null");
        Objects.requireNonNull(messengersClass, "Messenger class cannot be null");
        if (!messengersClass.isEnum()) {
            throw new IllegalArgumentException("Messenger class must be an enum");
        }
        registerDefaultMessages(provider, group, Objects.requireNonNull(messengersClass.getEnumConstants(), "The provided class is not an enum. Consider using registerMessenger(" + AuspiceUser.class.getSimpleName() + ", T[]) instead."));
    }

    public static <T extends DefinedMessenger> void registerDefaultMessages(AuspiceUser provider, NSedKey group, T[] messengers) {
        Objects.requireNonNull(provider, "Messengers provider cannot be null");
        Objects.requireNonNull(messengers, "Messenger values cannot be null");
        Map<NSedKey, DefinedMessenger[]> groupedDefMessages = registry.get(provider);
        if (groupedDefMessages == null) {
            groupedDefMessages = new LinkedHashMap<>(messengers.length);
            registry.put(provider, groupedDefMessages);
        }
        groupedDefMessages.put(group, messengers);
    }

    public static @Nullable Map<NSedKey, DefinedMessenger[]> getGroupedDefaultMessages(@NotNull AuspiceUser provider) {
        return registry.get(provider);
    }

    public static @Nullable DefinedMessenger[] getDefaultMessages(@NotNull AuspiceUser provider, NSedKey group) {
        Map<NSedKey, DefinedMessenger[]> a = getGroupedDefaultMessages(provider);
        return a == null ? null : a.get(group);
    }

    public static Locale localeOf(UUID uuid) {
        return AuspicePlayer.getAuspicePlayer(uuid).diversity();
    }

    public static Locale localeOf(Player player) {
        return AuspicePlayer.getAuspicePlayer(player).getLanguage();
    }

    public static Locale localeOf(OfflinePlayer offlinePlayer) {
        return AuspicePlayer.getAuspicePlayer(offlinePlayer).getLanguage();
    }

    public static Locale localeOf(CommandSender var0) {
        return var0 instanceof Player ? localeOf((Player) var0) : getDefaultLanguage();
    }

    public static Diversity getDefaultLanguage() {
        return SupportedLocale.EN;
    }
}

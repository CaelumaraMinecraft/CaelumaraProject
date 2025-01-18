package top.auspice.constants.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.config.functional.invoking.ConfigFunctionalInvokingData;
import top.auspice.config.placeholder.invoking.PlaceholderInvokable;
import top.auspice.configs.texts.placeholders.context.PlaceholderContextBuilder;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.centers.AuspiceDataCenter;
import top.auspice.diversity.Diversity;
import top.auspice.server.player.OfflinePlayer;

import java.util.UUID;

/**
 * AuspiceAPI 抽象的玩家
 * <p>
 * 获取这个玩家对应的 Bukkit 玩家的方法是:
 * <blockquote><pre>
 * Bukkit.getPlayer(auspicePlayer.getKey);
 * </pre></blockquote>
 */
public class AuspicePlayer extends KeyedAuspiceObject<UUID> implements PlaceholderInvokable {
    protected final @NotNull UUID key;
    private Diversity diversity;

    public AuspicePlayer(@NotNull UUID key) {
        this.key = key;
    }

    public AuspicePlayer(@NotNull UUID key, Diversity diversity) {
        this.key = key;
        this.diversity = diversity;
    }

    public @NotNull UUID getKey() {
        return this.key;
    }

    public @Nullable Object providePlaceholderAttribute(@NotNull String attributeName) {
        return switch (attributeName) {
            case "key", "uuid" -> this.key;
//            case "name" ->
            default -> null;
        };
    }

    public @Nullable Object invokePlaceholderFunction(@NotNull String functionName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context) {
        return null;
    }

    public @NotNull Diversity getDiversity() {
        if (this.diversity == null) this.diversity = Diversity.getDefault();
        return this.diversity;
    }

    public void setDiversity(Diversity diversity) {
        this.diversity = diversity;
    }

    public static @NotNull AuspicePlayer getAuspicePlayer(@NotNull OfflinePlayer player) {
        return getAuspicePlayer(player.getUniqueId());
    }

    /**
     * 通过玩家的 UUID 获取一个 {@link AuspicePlayer} 玩家实例
     * <p>
     * 不管在服务器内这个玩家是否存在, 这个方法总会返回一个非 null 的值. 若这个玩家的 id 在服务器内没存在过, 返回的 {@link AuspicePlayer} 实例将会是一个虚假的玩家.
     *
     * @param uuid 玩家的唯一的 id
     * @return 一个 {@link AuspicePlayer} 实例
     */
    public static @NotNull AuspicePlayer getAuspicePlayer(@NotNull UUID uuid) {
        AuspicePlayer ap = AuspiceDataCenter.get().getAuspicePlayerManager().getOrLoadData(uuid);
        return ap == null ? new AuspicePlayer(uuid) : ap;
    }
}

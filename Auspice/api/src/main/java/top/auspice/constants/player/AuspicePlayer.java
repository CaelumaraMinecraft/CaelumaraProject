package top.auspice.constants.player;

import net.aurika.annotations.data.LateInit;
import net.aurika.config.functional.invoking.ConfigFunctionalInvokingData;
import net.aurika.config.placeholder.invoking.PlaceholderInvokable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.text.placeholders.context.PlaceholderContextBuilder;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.centers.AuspiceDataCenter;
import top.auspice.diversity.Diversity;
import top.auspice.server.player.OfflinePlayer;

import java.util.UUID;

public interface AuspicePlayer extends KeyedAuspiceObject<UUID>, PlaceholderInvokable {

    @NotNull Diversity getDiversity();

    void setDiversity(Diversity diversity);

    static @NotNull AuspicePlayer getAuspicePlayer(@NotNull OfflinePlayer offlinePlayer) {
        return getAuspicePlayer(offlinePlayer.getUniqueId());
    }

    /**
     * 通过玩家的 UUID 获取一个 {@link AuspicePlayer} 玩家实例
     * <p>
     * 不管在服务器内这个玩家是否存在, 这个方法总会返回一个非 null 的值. 若这个玩家的 id 在服务器内没存在过, 返回的 {@link AuspicePlayer} 实例将会是一个虚假的玩家.
     *
     * @param id 玩家的唯一的 id
     * @return 一个 {@link AuspicePlayer} 实例
     */
    static @NotNull AuspicePlayer getAuspicePlayer(@NotNull UUID id) {
        AuspicePlayer ap = AuspiceDataCenter.get().getAuspicePlayerManager().getOrLoadData(id);
        return ap == null ? new AuspicePlayer.Impl(id) : ap;
    }

    class Impl extends KeyedAuspiceObject.Impl<UUID> implements AuspicePlayer {
        private @LateInit(by = "default diversity") Diversity diversity;

        public Impl(@NotNull UUID key) {
            super(key);
//            AuspiceDataCenter.get().getAuspicePlayerManager().cache(this, true);
        }

        public Impl(@NotNull UUID key, Diversity diversity) {
            super(key);
            this.diversity = diversity;
        }

        public @Nullable Object providePlaceholderAttribute(@NotNull String attributeName) {
            return switch (attributeName) {
                case "key", "uuid" -> this.dataKey();
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
    }
}

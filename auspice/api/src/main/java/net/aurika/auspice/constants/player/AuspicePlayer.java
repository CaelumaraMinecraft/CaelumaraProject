package net.aurika.auspice.constants.player;

import net.aurika.auspice.abstraction.AuspicePlayerOperator;
import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilderImpl;
import net.aurika.auspice.constants.base.KeyedAuspiceObject;
import net.aurika.auspice.data.centers.AuspiceDataCenter;
import net.aurika.auspice.platform.player.OfflinePlayer;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.common.annotation.data.LateInit;
import net.aurika.configuration.functional.invoking.ConfigFunctionalInvokingData;
import net.aurika.configuration.placeholder.invoking.PlaceholderInvokable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AuspicePlayer extends KeyedAuspiceObject<UUID>, AuspicePlayerOperator, PlaceholderInvokable {

  static @NotNull AuspicePlayer getAuspicePlayer(@NotNull OfflinePlayer offlinePlayer) {
    return getAuspicePlayer(offlinePlayer.uuid());
  }

  /**
   * 通过玩家的 UUID 获取一个 {@link AuspicePlayer} 玩家实例.
   * <p>不管在服务器内这个玩家是否存在, 这个方法总会返回一个非 null 的值. 若这个玩家的 id 在服务器内没存在过, 返回的 {@link AuspicePlayer} 实例将会是一个虚假的玩家.
   *
   * @param id 玩家的唯一的 id
   * @return 一个 {@link AuspicePlayer} 实例
   */
  static @NotNull AuspicePlayer getAuspicePlayer(@NotNull UUID id) {
    AuspicePlayer ap = AuspiceDataCenter.get().getAuspicePlayerManager().getOrLoadData(id);
    return ap == null ? new AuspicePlayer.Impl(id) : ap;
  }

  @NotNull Diversity diversity();

  void diversity(Diversity diversity);

  @Override
  default @NotNull AuspicePlayer auspicePlayer() {
    return this;
  }

  class Impl extends KeyedAuspiceObject.Impl<UUID> implements AuspicePlayer {

    private @LateInit(by = "default translation") Diversity diversity;

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

    public @Nullable Object invokePlaceholderFunction(@NotNull String functionName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilderImpl context) {
      return null;
    }

    public @NotNull Diversity diversity() {
      if (this.diversity == null) this.diversity = Diversity.globalDefault();
      return this.diversity;
    }

    public void diversity(Diversity diversity) {
      this.diversity = diversity;
    }

  }

}

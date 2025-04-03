package net.aurika.auspice.configs.messages.context;

import net.aurika.auspice.configs.messages.placeholders.PlaceholderParts;
import net.aurika.auspice.configs.messages.placeholders.context.LazyVariableProvider;
import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilder;
import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilderImpl;
import net.aurika.auspice.configs.messages.placeholders.context.VariableProvider;
import net.aurika.auspice.configs.messages.placeholders.target.BasePlaceholderTargetProvider;
import net.aurika.auspice.configs.messages.placeholders.target.PlaceholderTarget;
import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.platform.player.OfflinePlayer;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.context.TextContext;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.common.annotations.Getter;
import net.aurika.common.annotations.Setter;
import net.aurika.util.cache.single.CachedSupplier;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public interface MessageContext extends TextContext, PlaceholderContextBuilder, Cloneable {

  MessageContext DEFAULT = new MessageContextImpl();

  static @NotNull MessageContext messageContext() {
    return new MessageContextImpl();
  }

  @Contract("_ -> this")
  default MessageContext lang(@NotNull Diversity diversity) {
    Objects.requireNonNull(diversity, "diversity");
    this.diversity(diversity);
    return this;
  }

  @Contract("_ -> this")
  default MessageContext viewer(@NotNull OfflinePlayer offlinePlayer) {
    this.lang(MessageManager.diversityOf(offlinePlayer));
    return this;
  }

  @Contract("_ -> this")
  default MessageContext viewer(@NotNull AuspicePlayer player) {
    Validate.Arg.notNull(player, "player");
    this.lang(player.diversity());
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext placeholders(Object... placeholders) {
    TextContext.super.placeholders(placeholders);
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext ensureVariablesCapacity(int initialCapacity) {
    TextContext.super.ensureVariablesCapacity(initialCapacity);
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext inheritVariables(@NotNull PlaceholderContextBuilder other) {
    TextContext.super.inheritVariables(other);
    if (other instanceof MessageContext mc) {
      this.diversity(mc.diversity());
      this.usePrefix(mc.usePrefix());
    }

    return this;
  }

  @Override
  default @Nullable Object provideLocalVariable(@NotNull PlaceholderParts parts) {
    return PlaceholderTranslationContext.unwrapContextualPlaceholder(
        TextContext.super.provideLocalVariable(parts), this);
  }

  @Override
  @Contract("_, _ -> this")
  default MessageContext inheritTarget(@NotNull BasePlaceholderTargetProvider other, boolean overrideExisted) {
    TextContext.super.inheritTarget(other, overrideExisted);
    if (overrideExisted || primaryTarget() == null) {
      if (other.primaryTarget() instanceof Player) {
        this.withContext((Player) other.primaryTarget());
      } else if (other.primaryTarget() instanceof OfflinePlayer) {
        this.withContext((OfflinePlayer) other.primaryTarget());
      }
    }

    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext addAll(Map<String, Object> variables) {
    TextContext.super.addAll(variables);
    return this;
  }

  @Contract("_ -> this")
  default MessageContext addAll(PlaceholderContextBuilderImpl var1) {
    this.addAll(var1.variables());
    return this;
  }

  @Override
  @Contract("_, _ -> this")
  default MessageContext addChild(@NotNull String name, VariableProvider variableProvider) {
    TextContext.super.addChild(name, variableProvider);
    return this;
  }

  @Contract("_, _ -> this")
  default MessageContext addChild(String var1, Supplier<VariableProvider> var2) {
    addChild(var1, new LazyVariableProvider(var2));
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext addAllIfAbsent(Map<String, Object> raws) {
    TextContext.super.addAllIfAbsent(raws);
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext raws(Object... raws) {
    TextContext.super.raws(raws);
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext withContext(OfflinePlayer offlinePlayer) {
    if (offlinePlayer == null) {
      return this;
    } else {
      TextContext.super.withContext(offlinePlayer);
      this.raw("player", offlinePlayer);
      this.viewer(offlinePlayer);
      return this;
    }
  }

  @Override
  @Contract("_ -> this")
  default MessageContext withContext(Player player) {
    if (player != null) {
      TextContext.super.withContext(player);
      this.viewer(player);
      this.parse(
          "displayname", new CachedSupplier<>(() -> SoftService.VAULT.isAvailable() && ServiceVault.isAvailable(
              ServiceVault.Component.CHAT) ? ServiceVault.getDisplayName(player) : player.getDisplayName())
      );
      Objects.requireNonNull(player);
      this.parse("pure-displayname", new CachedSupplier<>(player::getDisplayName));
    }
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext withContext(CommandSender var1) {
    Objects.requireNonNull(var1);
    if (var1 instanceof Player) {
      return this.withContext((Player) var1);
    } else {
      return var1 instanceof OfflinePlayer ? this.withContext((OfflinePlayer) var1) : this;
    }
  }

  @Override
  @Contract("_ -> this")
  default MessageContext withContext(@NotNull PlaceholderTarget target) {
    TextContext.super.withContext(target);
    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext other(Player var1) {
    TextContext.super.other(var1);
    if (var1 != null) {
      this.raw("other_player", var1.getName());
    }

    return this;
  }

  @Override
  @Contract("_ -> this")
  default MessageContext other(@NotNull PlaceholderTarget target) {
    TextContext.super.other(target);
    return this;
  }

  @Override
  @Contract("-> this")
  default MessageContext resetPlaceholders() {
    TextContext.super.resetPlaceholders();
    return this;
  }

  @Override
  @Contract("_, _-> this")
  default MessageContext parse(String varName, Object object) {
    TextContext.super.parse(varName, object);
    return this;
  }

  @Override
  @Contract("_, _ -> this")
  default MessageContext raw(String varName, Object object) {
    TextContext.super.raw(varName, object);
    return this;
  }

  @Override
  @Contract("_, _ -> this")
  default MessageContext raw(String varName, Supplier<Object> objectSupplier) {
    TextContext.super.raw(varName, objectSupplier);
    return this;
  }

  @Contract("_ -> param1")
  default <T extends MessageContext> @NotNull T cloneInto(@NotNull T other) {
    TextContext.super.cloneInto((PlaceholderContextBuilderImpl) other);
    other.usePrefix(this.usePrefix());
    return other;
  }

  @Getter
  boolean ignoreColors();

  @Setter
  MessageContext ignoreColors(boolean ignoreColors);

  @Getter
  @Nullable Boolean usePrefix();

  @Setter
  MessageContext usePrefix(Boolean usePrefix);

  @Getter
  @NotNull Diversity diversity();

  @Setter
  MessageContext diversity(@NotNull Diversity diversity);

}

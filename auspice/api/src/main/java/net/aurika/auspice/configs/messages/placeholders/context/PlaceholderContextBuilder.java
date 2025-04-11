package net.aurika.auspice.configs.messages.placeholders.context;

import net.aurika.auspice.configs.messages.placeholders.PlaceholderParts;
import net.aurika.auspice.configs.messages.placeholders.target.BasePlaceholderTargetProvider;
import net.aurika.auspice.configs.messages.placeholders.target.PlaceholderTarget;
import net.aurika.auspice.configs.messages.placeholders.target.PlaceholderTargetProvider;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.platform.player.OfflinePlayer;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.text.compiler.placeholders.PlaceholderParser;
import net.aurika.common.annotation.Getter;
import net.aurika.common.annotation.Setter;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public interface PlaceholderContextBuilder extends LocalVariableProvider, PlaceholderTargetProvider, Cloneable {

  @Override
  default Object provideVariable(String name) {
    Object var2 = this.provideLocalVariable(new PlaceholderParts(name));
    if (var2 != null) {
      return var2;
    } else {
      Placeholder placeholder = PlaceholderParser.parse(name);
      var2 = placeholder.request(this);
      return placeholder.applyModifiers(var2);
    }
  }

  @Override
  default @Nullable Object provideLocalVariable(@NotNull PlaceholderParts parts) {
    Validate.Arg.notNull(parts, "parts");
    Object var2 = this.variables().get(parts.full());
    if (this.variables() != null && var2 != null) {
      return PlaceholderTranslationContext.unwrapPlaceholder(var2);
    } else {
      VariableProvider var3 = this.children().get(parts.id());
      if (this.children() != null && var3 != null) {
        return PlaceholderTranslationContext.unwrapPlaceholder(var3.provideVariable(parts.getParameterFromIndex(1)));
      } else {
        return this.unknownPlaceholderHandler() != null && (var2 = this.unknownPlaceholderHandler().provideVariable(
            parts.full())) != null ? PlaceholderTranslationContext.unwrapPlaceholder(var2) : null;
      }
    }
  }

  @Contract("_ -> param1")
  default <T extends PlaceholderContextBuilder> @NotNull T cloneInto(@NotNull T other) {
    Validate.Arg.notNull(other, "other");
    other.primaryTarget(this.primaryTarget());
    other.secondaryTarget(this.secondaryTarget());
    if (this.variables() != null) {
      other.variables(new HashMap<>(this.variables()));
    }

    if (this.children() != null) {
      other.children(this.children());
    }

    return other;
  }

  /**
   * Lets this {@linkplain PlaceholderContextBuilder} has children contexts map(empty).
   *
   * @param initialCapacity the initial capacity
   */
  default PlaceholderContextBuilder impregnate(int initialCapacity) {
    if (this.children() == null) this.children(new HashMap<>(initialCapacity));
    return this;
  }

  default PlaceholderContextBuilder addChild(@NotNull String name, VariableProvider variableProvider) {
    Validate.Arg.notNull(name, "name");
    if (name.contains("_")) {
      throw new IllegalArgumentException(
          "Child element name cannot contain underscore, consider nesting groups instead or using another name: " + name);
    } else {
      this.impregnate(1);
      if (this.children().put(name, variableProvider) != null) {
        throw new IllegalArgumentException("Child added twice: " + name + " -> " + variableProvider);
      } else {
        return this;
      }
    }
  }

  /**
   * Inherits placeholders from another {@linkplain PlaceholderContextBuilder}.
   *
   * @param other the other context
   */
  default PlaceholderContextBuilder inheritVariables(@NotNull PlaceholderContextBuilder other) {
    Validate.Arg.notNull(other, "other");
    this.addAllIfAbsent(other.variables());
    if (this.children() == null && other.children() != null) {
      this.children(other.children());
    }

    return this;
  }

  /**
   * Inherits targets context from another {@linkplain PlaceholderContextBuilder}.
   *
   * @param other           the other context
   * @param overrideExisted weather or not override targets that already exist
   */
  default PlaceholderContextBuilder inheritTarget(@NotNull BasePlaceholderTargetProvider other, boolean overrideExisted) {
    Validate.Arg.notNull(other, "other");
    if (overrideExisted || this.primaryTarget() == null) this.primaryTarget(other.primaryTarget());
    if (overrideExisted || this.secondaryTarget() == null) this.secondaryTarget(other.secondaryTarget());

    return this;
  }

  default PlaceholderContextBuilder addAll(Map<String, Object> variables) {
    if (this.variables() == null) {
      this.variables(variables);
    } else {
      this.variables().putAll(variables);
    }
    return this;
  }

  default PlaceholderContextBuilder addAllIfAbsent(Map<String, Object> var1) {
    if (var1 != null && !var1.isEmpty()) {
      if (this.variables() == null) {
        this.variables(new HashMap<>(var1));
      } else {

        for (Map.Entry<String, Object> stringObjectEntry : var1.entrySet()) {
          this.variables().putIfAbsent(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
      }
    }
    return this;
  }

  default PlaceholderContextBuilder raws(Object... raws) {
    this.variables(true, raws);
    return this;
  }

  default PlaceholderContextBuilder placeholders(Object... placeholders) {
    this.variables(false, placeholders);
    return this;
  }

  default PlaceholderContextBuilder variables(boolean checkSpType, Object @NotNull [] edits) {
    if (edits.length == 0) {
      return this;
    } else if (edits.length % 2 == 1) {
      throw new IllegalArgumentException(
          "Missing variable/replacement for one of edits, possibly: " + edits[edits.length - 1]);
    } else {
      this.addAll(PlaceholderParser.serializeVariables(checkSpType, this.variables(), edits));
      return this;
    }
  }

  default boolean hasContext() {
    return this.primaryTarget() != null;
  }

  default PlaceholderContextBuilder addTarget(String name, Object target) {
    this.targets().put(name, target);
    return this;
  }

  default PlaceholderContextBuilder withContext(Player player) {
    return player == null ? this : this.withContext((OfflinePlayer) player);
  }

  default PlaceholderContextBuilder withContext(@NotNull PlaceholderTarget target) {
    Validate.Arg.notNull(target, "target");
    Object var2 = target.provideTo(this);
    if (var2 != null) {
      this.primaryTarget(var2);
    }
    return this;
  }

  default PlaceholderContextBuilder withContext(OfflinePlayer offlinePlayer) {
    if (offlinePlayer != null) this.primaryTarget(offlinePlayer);
    return this;
  }

  default PlaceholderContextBuilder withContext(CommandSender messageReceiver) {
    Objects.requireNonNull(messageReceiver);
    if (messageReceiver instanceof Player) {
      return this.withContext((Player) messageReceiver);
    } else {
      return messageReceiver instanceof OfflinePlayer ? this.withContext((OfflinePlayer) messageReceiver) : this;
    }
  }

  default PlaceholderContextBuilder other(Player var1) {
    this.secondaryTarget(var1);
    return this;
  }

  default PlaceholderContextBuilder parse(String varName, Object object) {
    if (object != null) {
      this.ensureVariablesCapacity(12);
      this.variables().put(varName, PlaceholderTranslationContext.withDefaultContext(object));
    }
    return this;
  }

  default PlaceholderContextBuilder raw(String varName, Supplier<Object> objectSupplier) {
    return this.raw(varName, (Object) objectSupplier);
  }

  default PlaceholderContextBuilder other(@NotNull PlaceholderTarget target) {
    Validate.Arg.notNull(target, "target");
    Object var2 = target.provideTo(this);
    if (var2 != null) {
      this.secondaryTarget(var2);
    }
    return this;
  }

  default PlaceholderContextBuilder ensureVariablesCapacity(int initialCapacity) {
    if (this.variables() == null) {
      this.variables(new HashMap<>(initialCapacity));
    }
    return this;
  }

  default PlaceholderContextBuilder resetPlaceholders() {
    this.variables(null);
    return this;
  }

  default PlaceholderContextBuilder raw(String varName, Object object) {
    if (object != null) {
      this.ensureVariablesCapacity(12);
      this.variables().put(varName, object);
    }
    return this;
  }

  default Object getPlaceholder(String var1) {
    return this.variables() != null ? this.variables().get(var1) : null;
  }

  @Override
  default @NotNull PlaceholderContextBuilder switchTargets() {
    Object cache = this.primaryTarget();
    this.primaryTarget(this.secondaryTarget());
    this.secondaryTarget(cache);
    return this;
  }

  /**
   * Sets the children.
   *
   * @param children the children
   */
  @Setter
  PlaceholderContextBuilder children(Map<String, VariableProvider> children);

  /**
   * Gets the children.
   *
   * @return the children
   */
  @Getter
  Map<String, VariableProvider> children();

  /**
   * Gets variables of this {@linkplain PlaceholderContextBuilder}.
   *
   * @return the variables
   */
  @Getter
  Map<String, Object> variables();

  /**
   * Sets variables of this {@linkplain PlaceholderContextBuilder}.
   */
  @Setter
  PlaceholderTargetProvider variables(Map<String, Object> variables);

  /**
   * Gets the unknown placeholder handler.
   */
  @Getter
  VariableProvider unknownPlaceholderHandler();

  /**
   * Sets the unknown placeholder handler.
   */
  @Setter
  VariableProvider onUnknownPlaceholder(VariableProvider unknownPlaceholderHandler);

  @Override
  @Getter
  @Nullable Object primaryTarget();

  /**
   * Sets the primary target.
   */
  @Setter
  PlaceholderContextBuilder primaryTarget(Object target);

  @Override
  @Getter
  @Nullable Object secondaryTarget();

  /**
   * Sets the secondary target.
   */
  @Setter
  PlaceholderContextBuilder secondaryTarget(Object target);

  @Override
  @Getter
  @NotNull Map<String, Object> targets();

  PlaceholderContextBuilder clone();

  String toString();

}

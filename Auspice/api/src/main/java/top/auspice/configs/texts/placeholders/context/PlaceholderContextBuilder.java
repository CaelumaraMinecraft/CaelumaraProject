package top.auspice.configs.texts.placeholders.context;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.context.ConfigContext;
import top.auspice.configs.texts.compiler.PlaceholderTranslationContext;
import top.auspice.configs.texts.compiler.placeholders.Placeholder;
import top.auspice.configs.texts.compiler.placeholders.PlaceholderParser;
import top.auspice.configs.texts.placeholders.PlaceholderParts;
import top.auspice.configs.texts.placeholders.target.BasePlaceholderTargetProvider;
import top.auspice.configs.texts.placeholders.target.PlaceholderTarget;
import top.auspice.configs.texts.placeholders.target.PlaceholderTargetProvider;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.server.player.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class PlaceholderContextBuilder implements Cloneable, LocalPlaceholderProvider, PlaceholderTargetProvider, ConfigContext {
    public static final PlaceholderContextBuilder DEFAULT = new PlaceholderContextBuilder();
    protected Map<String, Object> placeholders;
    protected Map<String, PlaceholderProvider> children;
    protected PlaceholderProvider unknownPlaceholderHandler;
    protected Object primaryTarget;
    protected Object secondaryTarget;
    protected Map<String, Object> targets;

    public PlaceholderContextBuilder() {
    }

    public Object providePlaceholder(String name) {
        Object var2 = this.provideLocalPlaceholder(new PlaceholderParts(name));
        if (var2 != null) {
            return var2;
        } else {
            Placeholder placeholder = PlaceholderParser.parse(name);
            var2 = placeholder.request(this);
            return placeholder.applyModifiers(var2);
        }
    }

    public @Nullable Object provideLocalPlaceholder(@NotNull PlaceholderParts parts) {
        Object var2;
        if (this.placeholders != null && (var2 = this.placeholders.get(parts.getFull())) != null) {
            return PlaceholderTranslationContext.unwrapPlaceholder(var2);
        } else {
            PlaceholderProvider var3 = this.children.get(parts.getId());
            if (this.children != null && var3 != null) {
                return PlaceholderTranslationContext.unwrapPlaceholder(var3.providePlaceholder(parts.getParameterFrom(1)));
            } else {
                return this.unknownPlaceholderHandler != null && (var2 = this.unknownPlaceholderHandler.providePlaceholder(parts.getFull())) != null ? PlaceholderTranslationContext.unwrapPlaceholder(var2) : null;
            }
        }
    }

    public PlaceholderContextBuilder clone() {
        return this.cloneInto(new PlaceholderContextBuilder());
    }

    @Contract("_ -> param1")
    protected <T extends PlaceholderContextBuilder> T cloneInto(T other) {
        other.primaryTarget = this.primaryTarget;
        other.secondaryTarget = this.secondaryTarget;
        if (this.placeholders != null) {
            other.placeholders = new HashMap<>(this.placeholders);
        }

        if (this.children != null) {
            other.children = this.children;
        }

        return other;
    }

    public void setChildren(Map<String, PlaceholderProvider> children) {
        this.children = children;
    }

    public Map<String, PlaceholderProvider> getChildren() {
        return this.children;
    }

    public PlaceholderContextBuilder impregnate(int initialCapacity) {
        if (this.children == null) {
            this.children = new HashMap<>(initialCapacity);
        }

        return this;
    }

    public PlaceholderContextBuilder addChild(String var1, PlaceholderProvider var2) {
        if (var1.contains("_")) {
            throw new IllegalArgumentException("Child element name cannot contain underscore, consider nesting groups instead or using another name: " + var1);
        } else {
            this.impregnate(1);
            if (this.children.put(var1, var2) != null) {
                throw new IllegalArgumentException("Child added twice: " + var1 + " -> " + var2);
            } else {
                return this;
            }
        }
    }

    public PlaceholderContextBuilder inheritPlaceholders(PlaceholderContextBuilder var1) {
        this.addAllIfAbsent(var1.placeholders);
        if (this.children == null && var1.children != null) {
            this.children = var1.children;
        }

        return this;
    }

    public PlaceholderContextBuilder inheritContext(BasePlaceholderTargetProvider var1, boolean var2) {
        if (var2 || this.primaryTarget == null) {
            this.primaryTarget = var1.getPrimaryTarget();
        }

        if (var2 || this.secondaryTarget == null) {
            this.secondaryTarget = var1.getSecondaryTarget();
        }

        return this;
    }

    @Contract("_ -> this")
    public PlaceholderContextBuilder addAll(Map<String, Object> var1) {
        if (this.placeholders == null) {
            this.placeholders = var1;
        } else {
            this.placeholders.putAll(var1);
        }
        return this;
    }

    @Contract("_ -> this")
    public PlaceholderContextBuilder addAllIfAbsent(Map<String, Object> var1) {
        if (var1 != null && !var1.isEmpty()) {
            if (this.placeholders == null) {
                this.placeholders = new HashMap<>(var1);
            } else {

                for (Map.Entry<String, Object> stringObjectEntry : var1.entrySet()) {
                    this.placeholders.putIfAbsent(stringObjectEntry.getKey(), stringObjectEntry.getValue());
                }
            }
        }
        return this;
    }

    @Contract("_ -> this")
    public PlaceholderContextBuilder raws(Object... raws) {
        this.variables(true, raws);
        return this;
    }

    @Contract("_ -> this")
    public PlaceholderContextBuilder placeholders(Object... placeholders) {
        this.variables(false, placeholders);
        return this;
    }

    @Contract("_, _ -> this")
    protected PlaceholderContextBuilder variables(boolean checkSpType, Object[] edits) {
        if (edits.length == 0) {
            return this;
        } else if (edits.length % 2 == 1) {
            throw new IllegalArgumentException("Missing variable/replacement for one of edits, possibly: " + edits[edits.length - 1]);
        } else {
            this.addAll(PlaceholderParser.serializeVariables(checkSpType, this.placeholders, edits));
            return this;
        }
    }

    public Map<String, Object> getPlaceholders() {
        return this.placeholders;
    }

    public boolean hasContext() {
        return this.primaryTarget != null;
    }

    public PlaceholderContextBuilder addTarget(String name, Object target) {
        this.getTargets0().put(name, target);
        return this;
    }

    public PlaceholderContextBuilder withContext(Player player) {
        return player == null ? this : this.withContext((OfflinePlayer) player);
    }

//    public PlaceholderContextBuilder withContext(AuspicePlayer player) {
//        Player var2 = player.getPlayer();
//        if (var2 != null) {
//            this.withContext(var2);
//        }
//
//        OfflinePlayer var3 = player.getOfflinePlayer();
//        this.withContext(var3);
//        return this;
//    }

    public PlaceholderContextBuilder withContext(PlaceholderTarget var1) {
        Object var2 = var1.provideTo(this);
        if (var2 != null) {
            this.primaryTarget = var2;
        }
        return this;
    }

    public PlaceholderContextBuilder withContext(OfflinePlayer offlinePlayer) {
        if (offlinePlayer != null) {
            this.primaryTarget = offlinePlayer;
        }
        return this;
    }

    public PlaceholderContextBuilder withContext(CommandSender messageReceiver) {
        Objects.requireNonNull(messageReceiver);
        if (messageReceiver instanceof Player) {
            return this.withContext((Player) messageReceiver);
        } else {
            return messageReceiver instanceof OfflinePlayer ? this.withContext((OfflinePlayer) messageReceiver) : this;
        }
    }

    public PlaceholderProvider getUnknownPlaceholderHandler() {
        return this.unknownPlaceholderHandler;
    }

    public PlaceholderProvider onUnknownPlaceholder(PlaceholderProvider var1) {
        this.unknownPlaceholderHandler = var1;
        return this;
    }

    public PlaceholderContextBuilder other(PlaceholderTarget var1) {
        Object var2;
        if ((var2 = var1.provideTo(this)) == null) {
            return this;
        } else {
            this.secondaryTarget = var2;
            return this;
        }
    }

    public PlaceholderContextBuilder other(Player var1) {
        this.secondaryTarget = var1;
        return this;
    }

    public PlaceholderContextBuilder ensurePlaceholdersCapacity(int initialCapacity) {
        if (this.placeholders == null) {
            this.placeholders = new HashMap<>(initialCapacity);
        }

        return this;
    }

    public PlaceholderContextBuilder resetPlaceholders() {
        this.placeholders = null;
        return this;
    }

    public PlaceholderContextBuilder parse(String varName, Object object) {
        if (object != null) {
            this.ensurePlaceholdersCapacity(12);
            this.placeholders.put(varName, PlaceholderTranslationContext.withDefaultContext(object));
        }
        return this;
    }

    public PlaceholderContextBuilder raw(String varName, Supplier<Object> objectSupplier) {
        return this.raw(varName, (Object) objectSupplier);
    }

    public PlaceholderContextBuilder raw(String varName, Object object) {
        if (object != null) {
            this.ensurePlaceholdersCapacity(12);
            this.placeholders.put(varName, object);
        }
        return this;
    }

    public Object getPlaceholder(String var1) {
        return this.placeholders != null ? this.placeholders.get(var1) : null;
    }

    public String toString() {
        return
                "placeholderContextBuilder{ contextOf=" + this.primaryTarget +
                        ", other=" + this.secondaryTarget +
                        ", placeholders=" + (this.placeholders == null ? "{}" : this.placeholders.entrySet().stream().map((entry) -> entry.getKey() + '=' + entry.getValue()).toList()) +
                        " }";
    }

    public void setPrimaryTarget(Object target) {
        this.primaryTarget = target;
    }

    public void setSecondaryTarget(Object target) {
        this.secondaryTarget = target;
    }

    public @NotNull PlaceholderTargetProvider switchTargets() {
        Object var1 = this.primaryTarget;
        this.primaryTarget = this.secondaryTarget;
        this.secondaryTarget = var1;
        return this;
    }

    @Nullable
    public Object getPrimaryTarget() {
        return this.primaryTarget;
    }

    @Nullable
    public Object getSecondaryTarget() {
        return this.secondaryTarget;
    }

    @Internal
    @NotNull
    protected Map<String, Object> getTargets0() {
        if (this.targets == null) {
            this.targets = new HashMap<>(3);
        }

        return this.targets;
    }

    @NonNull
    public Map<String, Object> getTargets() {
        return this.getTargets0();
    }
}

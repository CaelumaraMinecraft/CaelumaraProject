package top.auspice.configs.texts.placeholders.context;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.LanguageManager;
import top.auspice.configs.texts.compiler.PlaceholderTranslationContext;
import top.auspice.configs.texts.placeholders.PlaceholderParts;
import top.auspice.configs.texts.placeholders.target.BasePlaceholderTargetProvider;
import top.auspice.configs.texts.placeholders.target.PlaceholderTarget;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityManager;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.server.player.OfflinePlayer;
import net.aurika.utils.Checker;
import top.auspice.utils.cache.single.CachedSupplier;
import top.auspice.utils.string.Strings;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class TextPlaceholderProvider extends PlaceholderContextBuilder implements Cloneable {
    public boolean ignoreColors = false;
    public Boolean usePrefix;
    private @NonNull Diversity diversity = LanguageManager.getDefaultLanguage();
    public static final TextPlaceholderProvider DEFAULT = new TextPlaceholderProvider();

    public TextPlaceholderProvider() {
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider placeholders(Object... placeholders) {
        super.placeholders(placeholders);
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider lang(@NotNull Diversity locale) {
        Objects.requireNonNull(locale);
        this.diversity = locale;
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider ensurePlaceholdersCapacity(int initialCapacity) {
        super.ensurePlaceholdersCapacity(initialCapacity);
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider viewer(@NotNull OfflinePlayer offlinePlayer) {
        this.lang(DiversityManager.localeOf(offlinePlayer));
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider viewer(@NotNull AuspicePlayer player) {
        Checker.Arg.notNull(player, "player");
        this.lang(player.getDiversity());
        return this;
    }

    public @NotNull Diversity getLanguage() {
        return this.diversity;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider inheritPlaceholders(PlaceholderContextBuilder other) {
        super.inheritPlaceholders(other);
        if (other instanceof TextPlaceholderProvider var2) {
            this.diversity = var2.diversity;
            if (var2.ignoreColors) {
                this.ignoreColors = true;
            }
        }

        return this;
    }

    public @Nullable Object provideLocalPlaceholder(@NotNull PlaceholderParts parts) {
        return PlaceholderTranslationContext.unwrapContextualPlaceholder(super.provideLocalPlaceholder(parts), this);
    }

    @Contract("_, _ -> this")
    public TextPlaceholderProvider inheritContext(BasePlaceholderTargetProvider var1, boolean var2) {
        super.inheritContext(var1, var2);
        if (var2 || super.primaryTarget == null) {
            if (var1.getPrimaryTarget() instanceof Player) {
                this.withContext((Player) var1.getPrimaryTarget());
            } else if (var1.getPrimaryTarget() instanceof OfflinePlayer) {
                this.withContext((OfflinePlayer) var1.getPrimaryTarget());
            }
        }

        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider addAll(Map<String, Object> var1) {
        super.addAll(var1);
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider addAll(PlaceholderContextBuilder var1) {
        this.addAll(var1.getPlaceholders());
        return this;
    }

    @Contract("_, _ -> this")
    public TextPlaceholderProvider addChild(String var1, PlaceholderProvider var2) {
        super.addChild(var1, var2);
        return this;
    }

    @Contract("_, _ -> this")
    public TextPlaceholderProvider addChild(String var1, Supplier<PlaceholderProvider> var2) {
        super.addChild(var1, new LazyPlaceholderProvider(var2));
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider addAllIfAbsent(Map<String, Object> raws) {
        super.addAllIfAbsent(raws);
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider raws(Object... raws) {
        super.raws(raws);
        return this;
    }

    @Contract("-> this")
    public TextPlaceholderProvider usePrefix() {
        return this.usePrefix(true);
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider usePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
        return this;
    }

    @Contract("-> this")
    public TextPlaceholderProvider ignoreColors() {
        this.ignoreColors = true;
        return this;
    }

    @Contract("-> this")
    public TextPlaceholderProvider dontIgnoreColors() {
        this.ignoreColors = false;
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider withContext(OfflinePlayer offlinePlayer) {
        if (offlinePlayer == null) {
            return this;
        } else {
            super.withContext(offlinePlayer);
            this.raw("player", offlinePlayer);
            this.viewer(offlinePlayer);
            return this;
        }
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider withContext(Player player) {
        if (player != null) {
            super.withContext(player);
            this.viewer(player);
            this.parse("displayname", new CachedSupplier<>(() -> SoftService.VAULT.isAvailable() && ServiceVault.isAvailable(ServiceVault.Component.CHAT) ? ServiceVault.getDisplayName(player) : player.getDisplayName()));
            Objects.requireNonNull(player);
            this.parse("pure-displayname", new CachedSupplier<>(player::getDisplayName));
        }
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider withContext(CommandSender var1) {
        Objects.requireNonNull(var1);
        if (var1 instanceof Player) {
            return this.withContext((Player) var1);
        } else {
            return var1 instanceof OfflinePlayer ? this.withContext((OfflinePlayer) var1) : this;
        }
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider withContext(PlaceholderTarget target) {
        super.withContext(target);
        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider other(Player var1) {
        super.other(var1);
        if (var1 != null) {
            this.raw("other_player", var1.getName());
        }

        return this;
    }

    @Contract("_ -> this")
    public TextPlaceholderProvider other(PlaceholderTarget var1) {
        super.other(var1);
        return this;
    }

    @Contract("-> this")
    public TextPlaceholderProvider resetPlaceholders() {
        super.resetPlaceholders();
        return this;
    }

    @Contract("_, _-> this")
    public TextPlaceholderProvider parse(String varName, Object object) {
        super.parse(varName, object);
        return this;
    }

    @Contract("_, _ -> this")
    public TextPlaceholderProvider raw(String varName, Object object) {
        super.raw(varName, object);
        return this;
    }

    @Contract("_, _ -> this")
    public TextPlaceholderProvider raw(String varName, Supplier<Object> objectSupplier) {
        super.raw(varName, objectSupplier);
        return this;
    }

    public String toString() {
        return "MessageBuilder{" +
                "context=" + super.primaryTarget +
                ", ignoreColors=" + this.ignoreColors +
                ", prefix=" + this.usePrefix +
                ", other=" + super.secondaryTarget +
                ", Children=" + Strings.associatedArrayMap(super.children) +
                ", placeholders=" + (super.placeholders == null ? "{}" : super.placeholders.entrySet().stream().map((entry) -> String.valueOf(entry.getKey()) + '=' + entry.getValue()).toList()) + " }";
    }

    public TextPlaceholderProvider clone() {
        return this.cloneInto(new TextPlaceholderProvider());
    }

    protected <T extends TextPlaceholderProvider> T cloneInto(T other) {
        super.cloneInto((PlaceholderContextBuilder) other);
        other.usePrefix = this.usePrefix;
        other.ignoreColors = this.ignoreColors;
        return other;
    }
}

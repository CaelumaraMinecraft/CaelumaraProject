package net.aurika.auspice.text.compiler.placeholders.types;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import me.clip.placeholderapi.expansion.manager.LocalExpansionManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import net.aurika.text.placeholders.PlaceholderParts;
import net.aurika.text.placeholders.context.LocalPlaceholderProvider;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import net.aurika.text.placeholders.target.BasePlaceholderTargetProvider;
import net.aurika.text.placeholders.target.PlaceholderTargetProvider;
import net.aurika.auspice.services.managers.SoftService;

import java.util.List;
import java.util.Objects;

/**
 * 一般的占位符
 * 比如来自于 PlaceholderAPI 的占位符
 * NormalPlaceholder
 */
public final class ExternalOrLocalPlaceholder extends AbstractPlaceholder {
    private final boolean a;
    @NotNull
    private final String b;
    @Nullable
    private final String c;

    public ExternalOrLocalPlaceholder(@NotNull String var1, boolean var2, @NotNull String var3, @Nullable String var4, @Nullable String var5, @NotNull List<PlaceholderModifier> var6) {
        super(var1, var5, var6);
        Objects.requireNonNull(var3, "");
        this.a = var2;
        this.b = var3;
        this.c = var4;
    }

    @NotNull
    public String asString(boolean surround) {
        String var2 = (this.a ? "rel_" : "") + this.b + (this.c != null ? "_" + this.c : "");
        return this.getCommonString(surround, var2);
    }

    @Nullable
    public Object request(@NotNull PlaceholderProvider provider) {
        Objects.requireNonNull(provider, "");
        Object var2;
        if (!this.a && provider instanceof LocalPlaceholderProvider && (var2 = ((LocalPlaceholderProvider) provider).provideLocalPlaceholder(new PlaceholderParts(this.b + (this.c != null ? "_" + this.c : "")))) != null) {
            return var2;
        } else if (!(provider instanceof PlaceholderTargetProvider)) {
            throw this.error("Cannot use placeholder (" + this.asString(true) + ") here with no context: " + provider);
        } else {
            BasePlaceholderTargetProvider var3 = ((PlaceholderTargetProvider) provider).getTargetProviderFor(this.getPointer());
            if (!SoftService.PLACEHOLDERAPI.isAvailable()) {
                return null;
            } else {
                LocalExpansionManager var10000 = PlaceholderAPIPlugin.getInstance().getLocalExpansionManager();
                Objects.requireNonNull(var10000, "");
                PlaceholderExpansion var6 = var10000.getExpansion(this.b);
                if (var6 == null) {
                    return null;
                } else {
                    Objects.requireNonNull(var6);
                    String var7;
                    Object primaryTarget;
                    if (var6 instanceof Relational var8 && var3.getSecondaryTarget() instanceof Player && var3.getPrimaryTarget() instanceof Player) {
                        primaryTarget = var3.getPrimaryTarget();
                        Objects.requireNonNull(primaryTarget);
                        Player var10 = (Player)primaryTarget;
                        Object var11 = var3.getSecondaryTarget();
                        Objects.requireNonNull(var11);
                        Player var12 = (Player)var11;
                        String var10003 = this.c;
                        if (var10003 == null) {
                            var10003 = "";
                        }

                        var7 = var8.onPlaceholderRequest(var10, var12, var10003);
                    } else {
                        if (!(var3.getPrimaryTarget() instanceof OfflinePlayer)) {
                            return null;
                        }

                        primaryTarget = var3.getPrimaryTarget();
                        Objects.requireNonNull(primaryTarget);
                        OfflinePlayer var9 = (OfflinePlayer)primaryTarget;
                        String var10002 = this.c;
                        if (var10002 == null) {
                            var10002 = "";
                        }

                        var7 = var6.onRequest(var9, var10002);
                    }

                    String var5 = var7;
                    return wrapWithDefaultContextProvider(var5);
                }
            }
        }
    }
}


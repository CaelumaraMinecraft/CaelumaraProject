package net.aurika.auspice.configs.messages.placeholders;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import top.auspice.constants.top.ExpressionBasedTopData;
import top.auspice.constants.top.TopData;
import net.aurika.text.AuspiceLang;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.text.compiler.placeholders.PlaceholderParser;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderFunctionInvoker;
import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilderImpl;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class TopPlaceholders<K, V> extends FunctionalPlaceholder {

    @Nullable
    public abstract ExpressionBasedTopData<K, V> getTopData$core(@NotNull String var1);

    @NotNull
    public abstract V getContextObject$core(@NotNull KingdomsPlaceholderTranslationContext var1);

    @NotNull
    public abstract PlaceholderContextBuilderImpl createContext$core(@NotNull V var1);

    private ExpressionBasedTopData<K, V> a(PlaceholderFunctionInvoker invoker, String string) {
        ExpressionBasedTopData<K, V> var3 = this.getTopData$core(string);
        if (var3 == null) {
            invoker.invalidArg("type", "Unknown top kingdom data type named '" + string + '\'');
            return null;
        } else {
            return var3;
        }
    }

    @PhFn
    @Nullable
    public final Object value(@NotNull KingdomsPlaceholderTranslationContext context,
                              @NotNull PlaceholderFunctionInvoker invoker,
                              @PhParam(name = "type") @NotNull String type) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(invoker);
        Objects.requireNonNull(type);
        ExpressionBasedTopData<K, V> var10000 = this.a(invoker, type);
        return var10000 == null ? null : var10000.getTopValueOf(this.getContextObject$core(context));
    }

    @PhFn
    @Nullable
    public final Object position(@NotNull KingdomsPlaceholderTranslationContext context,
                                 @NotNull PlaceholderFunctionInvoker invoker,
                                 @PhParam(name = "type") @NotNull String type) {
        Objects.requireNonNull(context, "");
        Objects.requireNonNull(invoker, "");
        Objects.requireNonNull(type, "");
        ExpressionBasedTopData<K, V> var10000 = this.a(invoker, type);
        if (var10000 == null) {
            return null;
        } else {
            return var10000.getPositionOf(this.getContextObject$core(context)).orElse(var10000.size());
        }
    }

    @PhFn
    @Nullable
    public final Object isIncluded(@NotNull KingdomsPlaceholderTranslationContext context,
                                   @NotNull PlaceholderFunctionInvoker invoker,
                                   @PhParam(name = "type") @NotNull String type) {
        Objects.requireNonNull(context, "");
        Objects.requireNonNull(invoker, "");
        Objects.requireNonNull(type, "");
        ExpressionBasedTopData<K, V> var10000 = this.a(invoker, type);
        return var10000 == null ? null : var10000.isIncluded(this.getContextObject$core(context));
    }

    @PhFn
    @Nullable
    public final Object at(@NotNull PlaceholderFunctionInvoker var1,
                           @PhParam(name = "pos") int pos,
                           @PhParam(name = "of") @NotNull String of,
                           @PhParam(name = "type") @NotNull String type) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(of);
        Objects.requireNonNull(type);
        if (pos <= 0) {
            var1.invalidArg("pos", "Position must be a positive number, got " + pos);
            return null;
        } else {
            TopData<V> var10000 = this.a(var1, type);
            if (var10000 == null) {
                return null;
            } else {
                Placeholder placeholder = PlaceholderParser.parse(of, true);
                KingdomsPlaceholder kingdomsPlaceholder = placeholder instanceof KingdomsPlaceholder ? (KingdomsPlaceholder) placeholder : null;
                if (kingdomsPlaceholder == null) {
                    return null;
                } else {
                    V v = var10000.getTopPosition(pos).orElse(null);
                    if (v == null) {
                        Object[] var6 = new Object[2];
                        var6[0] = "position";
                        var6[1] = pos;
                        return AuspiceLang.PLACEHOLDERS_TOP_NOT_FOUND.parse(var6);
                    } else {
                        return kingdomsPlaceholder.request(this.createContext$core(v));
                    }
                }
            }
        }
    }
}

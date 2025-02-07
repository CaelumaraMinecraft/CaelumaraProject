package top.auspice.configs.texts.placeholders;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.top.TopData;
import top.auspice.configs.texts.AuspiceLang;
import top.auspice.configs.texts.compiler.placeholders.Placeholder;
import top.auspice.configs.texts.compiler.placeholders.PlaceholderParser;
import top.auspice.configs.texts.compiler.placeholders.functions.PlaceholderFunctionInvoker;
import top.auspice.configs.texts.placeholders.context.PlaceholderContextBuilder;

public abstract class PositionalTopPlaceholders<V> extends FunctionalPlaceholder {
    public PositionalTopPlaceholders() {
    }

    @NotNull
    public abstract TopData<V> getTopData$core(@NotNull KingdomsPlaceholderTranslationContext var1);

    @NotNull
    public abstract PlaceholderContextBuilder createContext$core(@NotNull V var1);

    @PlaceholderFunction
    public final boolean has(@NotNull KingdomsPlaceholderTranslationContext var1, @PlaceholderParameter(name = "position") int var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.getTopData$core(var1).getTopPosition(var2).isPresent();
    }

    @PlaceholderFunction
    @Nullable
    public final Object at(@NotNull KingdomsPlaceholderTranslationContext var1, @NotNull PlaceholderFunctionInvoker var2, @PlaceholderParameter(name = "pos") int var3, @PlaceholderParameter(name = "of") @NotNull String var4) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var4, "");
        if (var3 <= 0) {
            var2.invalidArg("pos", "Position must be a positive number, got " + var3);
            return null;
        } else {
            TopData<V> var5 = this.getTopData$core(var1);
            Placeholder var10000 = PlaceholderParser.parse(var4, false);
            if (var10000 == null) {
                return null;
            } else {
                V var6 = var5.getTopPosition(var3).orElse(null);
                if (var6 == null) {
                    AuspiceLang var9 = AuspiceLang.PLACEHOLDERS_TOP_NOT_FOUND;
                    Object[] var7;
                    (var7 = new Object[2])[0] = "position";
                    var7[1] = var3;
                    return var9.parse(var7);
                } else {
                    return var10000.request(this.createContext$core(var6));
                }
            }
        }
    }
}

package net.aurika.auspice.configs.messages.placeholders;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilderImpl;
import net.aurika.auspice.constants.top.TopData;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.text.compiler.placeholders.PlaceholderParser;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderFunctionInvoker;
import net.aurika.text.AuspiceLang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PositionalTopPlaceholders<V> extends FunctionalPlaceholder {

  public PositionalTopPlaceholders() {
  }

  @NotNull
  public abstract TopData<V> getTopData$core(@NotNull KingdomsPlaceholderTranslationContext var1);

  @NotNull
  public abstract PlaceholderContextBuilderImpl createContext$core(@NotNull V var1);

  @PhFn
  public final boolean has(@NotNull KingdomsPlaceholderTranslationContext var1, @PhParam(name = "position") int var2) {
    Intrinsics.checkNotNullParameter(var1, "");
    return this.getTopData$core(var1).getTopPosition(var2).isPresent();
  }

  @PhFn
  @Nullable
  public final Object at(@NotNull KingdomsPlaceholderTranslationContext var1, @NotNull PlaceholderFunctionInvoker var2, @PhParam(name = "pos") int var3, @PhParam(name = "of") @NotNull String var4) {
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

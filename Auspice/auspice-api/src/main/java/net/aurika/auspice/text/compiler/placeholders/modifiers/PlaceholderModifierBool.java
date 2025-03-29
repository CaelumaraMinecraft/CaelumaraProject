package net.aurika.auspice.text.compiler.placeholders.modifiers;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.text.AuspiceLang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PlaceholderModifierBool implements PlaceholderModifier {

  @NotNull
  public static final PlaceholderModifierBool INSTANCE = new PlaceholderModifierBool();
  @NotNull
  private static final String a = "bool";

  private PlaceholderModifierBool() {
  }

  @NotNull
  public String getName() {
    return a;
  }

  @NotNull
  public Object apply(@NotNull Placeholder placeholder, @Nullable Object object) {
    Intrinsics.checkNotNullParameter(placeholder, "");
    boolean var10000;
    if (object instanceof Boolean) {
      var10000 = (Boolean) object;
    } else {
      PlaceholderModifier var3;
      if (object instanceof String) {
        Boolean var4 = StringsKt.toBooleanStrictOrNull((String) object);
        if (var4 == null) {
          var3 = this;
          throw new PlaceholderModifierException(
              "Unsupported placeholder value for modifier '" + var3.getName() + "' with value (" + (object != null ? object.getClass() : null) + ") " + object + " for placeholder: " + placeholder);
        }

        var10000 = var4;
      } else {
        if (!(object instanceof Number)) {
          var3 = this;
          throw new PlaceholderModifierException(
              "Unsupported placeholder value for modifier '" + var3.getName() + "' with value (" + (object != null ? object.getClass() : null) + ") " + object + " for placeholder: " + placeholder);
        }

        var10000 = ((Number) object).doubleValue() > 0.0;
      }
    }

    return var10000 ? AuspiceLang.ENABLED : AuspiceLang.DISABLED;
  }

  public boolean isSupported(@NotNull Class<?> type) {
    Intrinsics.checkNotNullParameter(type, "");
    return Boolean.TYPE.isAssignableFrom(type) || String.class.isAssignableFrom(type) || Number.class.isAssignableFrom(
        type);
  }

  @NotNull
  public Class<?> getOutputType() {
    return Boolean.TYPE;
  }

}

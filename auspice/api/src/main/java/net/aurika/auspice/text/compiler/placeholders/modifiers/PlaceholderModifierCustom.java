package net.aurika.auspice.text.compiler.placeholders.modifiers;

import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.configuration.accessor.ClearlyConfigAccessor;
import net.aurika.util.string.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PlaceholderModifierCustom implements PlaceholderModifier {

  private final @NotNull String name;
  private final @NotNull String b;
  private final @NotNull String c;

  public PlaceholderModifierCustom(@NotNull String var1) {
    Objects.requireNonNull(var1);
    this.name = var1;
    ClearlyConfigAccessor var10000 = AuspiceGlobalConfig.PLACEHOLDERS_FORMATS.getManager().getSection().noDefault();
    Objects.requireNonNull(var10000);
    ClearlyConfigAccessor var5 = var10000;
    String[] var3 = new String[1];
    var3[0] = this.getName();
    String[] var2;
    ClearlyConfigAccessor var4 = var5.gotoSection(var3);
    if ((var4) != null) {
      String var10001 = var4.getString(new String[]{"default"});
      Objects.requireNonNull(var10001, "");
      this.b = var10001;
      var10001 = var4.getString(new String[]{"normal"});
      Objects.requireNonNull(var10001, "");
      this.c = var10001;
    } else {
      var10000 = AuspiceGlobalConfig.PLACEHOLDERS_FORMATS.getManager().getSection().noDefault();
      (var2 = new String[1])[0] = this.getName();
      var1 = var10000.getString(var2);
      Objects.requireNonNull(var1);
      this.b = var1;
      this.c = var1;
    }
  }

  public @NotNull String getName() {
    return this.name;
  }

  @NotNull
  public Object apply(@NotNull Placeholder placeholder, @Nullable Object object) {
    Objects.requireNonNull(placeholder);
    Object var3 = object;
    boolean var4;
    if (object != null && !((object instanceof String ? (String) object : null) != null && ((String) object).isEmpty())) {
      var4 = false;
    } else {
      var4 = true;
      if (placeholder instanceof KingdomsPlaceholder && (var3 = ((KingdomsPlaceholder) placeholder).identifier.getConfiguredDefaultValue()) == null) {
        var3 = ((KingdomsPlaceholder) placeholder).identifier.getDefault();
      }
    }

    PlaceholderTranslationContext var10000 = PlaceholderTranslationContext.withDefaultContext(
        Strings.replaceOnce(var4 ? this.b : this.c, "%", String.valueOf(var3)));
    Objects.requireNonNull(var10000, "");
    return var10000;
  }

  public boolean isSupported(@NotNull Class<?> type) {
    Objects.requireNonNull(type, "");
    return true;
  }

  @NotNull
  public Class<?> getOutputType() {
    return String.class;
  }

}

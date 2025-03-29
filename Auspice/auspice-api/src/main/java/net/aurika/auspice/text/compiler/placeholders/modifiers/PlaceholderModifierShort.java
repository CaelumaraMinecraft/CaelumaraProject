package net.aurika.auspice.text.compiler.placeholders.modifiers;

import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.utils.math.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PlaceholderModifierShort implements PlaceholderModifier {

  @NotNull
  public static final PlaceholderModifierShort INSTANCE = new PlaceholderModifierShort();

  private PlaceholderModifierShort() {
  }

  @NotNull
  public String getName() {
    return "short";
  }

  @NotNull
  public Object apply(@NotNull Placeholder placeholder, @Nullable Object object) {
    Objects.requireNonNull(placeholder);
    if (!(object instanceof Number)) {
      throw new PlaceholderModifierException(
          "Unsupported placeholder value for modifier '" + this.getName() + "' with value (" + (object != null ? object.getClass() : null) + ") " + object + " for placeholder: " + placeholder);
    } else {
      String var10000 = MathUtils.getShortNumber((Number) object);
      Objects.requireNonNull(var10000);
      return var10000;
    }
  }

  public boolean isSupported(@NotNull Class<?> type) {
    Objects.requireNonNull(type);
    return Number.class.isAssignableFrom(type);
  }

  @NotNull
  public Class<?> getOutputType() {
    return String.class;
  }

}


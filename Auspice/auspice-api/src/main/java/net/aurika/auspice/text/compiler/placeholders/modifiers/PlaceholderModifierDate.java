package net.aurika.auspice.text.compiler.placeholders.modifiers;

import jline.internal.Nullable;
import net.aurika.auspice.text.compiler.builders.TextObjectBuilder;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.utils.time.TimeFormatter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PlaceholderModifierDate implements PlaceholderModifier {

  @NotNull
  public static final PlaceholderModifierDate INSTANCE = new PlaceholderModifierDate();

  private PlaceholderModifierDate() {
  }

  @NotNull
  public String getName() {
    return "date";
  }

  @NotNull
  public Object apply(@NotNull Placeholder placeholder, @Nullable Object object) {
    Objects.requireNonNull(placeholder, "");
    if (!(object instanceof Number)) {
      PlaceholderModifier var3 = this;
      throw new PlaceholderModifierException(
          "Unsupported placeholder value for modifier '" + var3.getName() + "' with value (" + (object != null ? object.getClass() : null) + ") " + object + " for placeholder: " + placeholder);
    } else {
      TextObjectBuilder var10000 = TimeFormatter.dateOf(((Number) object).longValue());
      Objects.requireNonNull(var10000, "");
      return var10000;
    }
  }

  public boolean isSupported(@NotNull Class<?> type) {
    Objects.requireNonNull(type, "");
    return Number.class.isAssignableFrom(type);
  }

  @NotNull
  public Class<?> getOutputType() {
    return TextObjectBuilder.class;
  }

}


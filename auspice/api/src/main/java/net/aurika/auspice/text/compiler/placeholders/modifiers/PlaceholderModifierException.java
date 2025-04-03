package net.aurika.auspice.text.compiler.placeholders.modifiers;

import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PlaceholderModifierException extends RuntimeException {

  public PlaceholderModifierException(@NotNull String msg) {
    super(Objects.requireNonNull(msg));
  }

  public static PlaceholderModifierException unsupported(@NotNull PlaceholderModifier modifier, @NotNull Placeholder placeholder, @Nullable Object obj) {
    return new PlaceholderModifierException(
        "Unsupported placeholder value for modifier '" + modifier.getName() + "' with value (" + (obj != null ? obj.getClass() : null) + ") " + obj + " for placeholder: " + placeholder);
  }

}

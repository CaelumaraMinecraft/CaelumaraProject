package net.aurika.config.placeholder;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlaceholderTranslationException extends RuntimeException {

  @NotNull
  private final Placeholder placeholder;

  public PlaceholderTranslationException(@NotNull Placeholder placeholder, @NotNull String problem) {
    super(Objects.requireNonNull(problem) + " for '" + Objects.requireNonNull(placeholder).asString(true) + '\'');
    this.placeholder = placeholder;
  }

  @NotNull
  public final Placeholder getPlaceholder() {
    return this.placeholder;
  }

}

package net.aurika.config.placeholder.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MacroPlaceholder extends AbstractPlaceholder {

  public MacroPlaceholder(@NotNull String originalString, @Nullable String pointer) {
    super(originalString, pointer);
  }

}

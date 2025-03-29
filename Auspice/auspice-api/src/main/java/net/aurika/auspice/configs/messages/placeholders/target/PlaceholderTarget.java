package net.aurika.auspice.configs.messages.placeholders.target;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlaceholderTarget {

  @Nullable Object provideTo(@NotNull PlaceholderTargetProvider placeholderTargetProvider);

}

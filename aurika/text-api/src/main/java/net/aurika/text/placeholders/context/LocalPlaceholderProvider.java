package net.aurika.text.placeholders.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.text.placeholders.PlaceholderParts;

public interface LocalPlaceholderProvider extends PlaceholderProvider {
    @Nullable Object provideLocalPlaceholder(@NotNull PlaceholderParts parts);
}

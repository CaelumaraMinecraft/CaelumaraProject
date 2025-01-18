package top.auspice.configs.texts.placeholders.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.placeholders.PlaceholderParts;

public interface LocalPlaceholderProvider extends PlaceholderProvider {
    @Nullable Object provideLocalPlaceholder(@NotNull PlaceholderParts parts);
}

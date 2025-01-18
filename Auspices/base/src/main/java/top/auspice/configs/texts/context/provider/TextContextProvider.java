package top.auspice.configs.texts.context.provider;

import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

public interface TextContextProvider {
    @NotNull TextPlaceholderProvider getTextContext();
}
